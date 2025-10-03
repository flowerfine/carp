/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sliew.carp.module.datasource.service.impl;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.jdbc.MySQLDataSourceProperties;
import cn.sliew.carp.module.datasource.service.CarpGravitinoMetalakeService;
import cn.sliew.carp.module.datasource.service.convert.GravitinoCatalogConvert;
import cn.sliew.carp.module.datasource.service.convert.GravitinoMetalakeConvert;
import cn.sliew.carp.module.datasource.service.convert.GravitinoSchemaConvert;
import cn.sliew.carp.module.datasource.service.convert.GravitinoTableConvert;
import cn.sliew.carp.module.datasource.service.dto.*;
import cn.sliew.milky.common.util.JacksonUtil;
import org.apache.gravitino.*;
import org.apache.gravitino.client.GravitinoAdminClient;
import org.apache.gravitino.client.GravitinoMetalake;
import org.apache.gravitino.dto.CatalogDTO;
import org.apache.gravitino.dto.rel.TableDTO;
import org.apache.gravitino.dto.util.DTOConverters;
import org.apache.gravitino.rel.Table;
import org.apache.gravitino.rel.TableCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarpGravitinoMetalakeServiceImpl implements CarpGravitinoMetalakeService {

    @Autowired
    private GravitinoAdminClient adminClient;

    @Override
    public PageResult<GravitinoMetalakeDTO> page(PageParam param) {
        List<GravitinoMetalakeDTO> list = GravitinoMetalakeConvert.INSTANCE.toDto(Arrays.asList(adminClient.listMetalakes()));
        return PageUtil.buildPageResult(param, list);
    }

    @Override
    public List<GravitinoCatalogDTO> listCatalogs(String metalakeName) {
        GravitinoMetalake gravitinoMetalake = adminClient.loadMetalake(metalakeName);
        Catalog[] catalogs = gravitinoMetalake.listCatalogsInfo();
        List<CatalogDTO> catalogDTOList = Arrays.asList(catalogs).stream().map(catalog -> (CatalogDTO) catalog).collect(Collectors.toList());
        return GravitinoCatalogConvert.INSTANCE.toDto(catalogDTOList);
    }

    @Override
    public List<GravitinoSchemaDTO> listSchema(String metalakeName, String catalogName) {
        GravitinoMetalake metalake = adminClient.loadMetalake(metalakeName);
        Catalog catalog = metalake.loadCatalog(catalogName);
        SupportsSchemas schemas = catalog.asSchemas();
        List<Schema> schemaDTOList = Arrays.stream(schemas.listSchemas()).map(schemas::loadSchema)
                .toList();
        return GravitinoSchemaConvert.INSTANCE.toDto(schemaDTOList);
    }

    @Override
    public List<String> listTables(String metalakeName, String catalogName, String schemaName) {
        GravitinoMetalake metalake = adminClient.loadMetalake(metalakeName);
        Catalog catalog = metalake.loadCatalog(catalogName);
        TableCatalog tableCatalog = catalog.asTableCatalog();
        NameIdentifier[] nameIdentifiers = tableCatalog.listTables(Namespace.of(schemaName));
        return Arrays.stream(nameIdentifiers).map(NameIdentifier::name).toList();
    }

    @Override
    public GravitinoTableDTO getTable(String metalakeName, String catalogName, String schemaName, String tableName) {
        GravitinoMetalake metalake = adminClient.loadMetalake(metalakeName);
        Catalog catalog = metalake.loadCatalog(catalogName);
        TableCatalog tableCatalog = catalog.asTableCatalog();
        Namespace namespace = Namespace.of(schemaName);
        NameIdentifier nameIdentifier = NameIdentifier.of(namespace, tableName);
        Table table = tableCatalog.loadTable(nameIdentifier);
        return GravitinoTableConvert.INSTANCE.toDto(table);
    }

    @Override
    public void tryAddMetalake(String metalakeName) {
        if (adminClient.metalakeExists(metalakeName) == false) {
            adminClient.createMetalake(metalakeName, "init metalake by system", Collections.emptyMap());
        }
    }

    @Override
    public void tryAddCatalog(String metalakeName, DsInfoDTO dto) {
        GravitinoMetalake metalake = adminClient.loadMetalake(metalakeName);
        if (metalake.catalogExists(dto.getName()) == false) {
            switch (dto.getDsType().getType()) {
                case MYSQL:
                    initMySQL(metalake, dto.getName(), dto);
                    break;
                case POSTGRESQL:
                    break;
                case HIVE:
                    break;
                case ICEBERG:
                    break;
                case DORIS:
                    break;
                case KAFKA:
                    break;
                case HDFS:
                    break;
                default:
            }
        }
    }

    private void initMySQL(GravitinoMetalake metalake, String catalogName, DsInfoDTO dsInfoDTO) {
        DataSourceInfo dataSourceInfo = JacksonUtil.toObject(JacksonUtil.toJsonNode(dsInfoDTO), DataSourceInfo.class);
        MySQLDataSourceProperties props = (MySQLDataSourceProperties) dataSourceInfo.getProps();
        Map<String, String> properties = new HashMap<>();
        properties.put("jdbc-driver", props.getDriverClassName());
        properties.put("jdbc-url", props.getUrl());
        properties.put("jdbc-user", props.getUser());
        properties.put("jdbc-password", props.getPassword());
        metalake.createCatalog(catalogName, Catalog.Type.RELATIONAL, "jdbc-mysql", dataSourceInfo.getRemark(), properties);
    }

    @Override
    public void tryUpdateCatalog(String metalakeName, DsInfoDTO dto) {

    }

    @Override
    public void tryDeleteCatalog(String metalakeName, DsInfoDTO dto) {
        GravitinoMetalake metalake = adminClient.loadMetalake(metalakeName);
    }
}
