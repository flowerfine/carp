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
import cn.sliew.carp.framework.spring.util.PageUtil;
import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.jdbc.MySQLDataSourceProperties;
import cn.sliew.carp.module.datasource.service.CarpGravitinoMetalakeService;
import cn.sliew.carp.module.datasource.service.convert.GravitinoCatalogConvert;
import cn.sliew.carp.module.datasource.service.convert.GravitinoMetalakeConvert;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoCatalogDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoMetalakeDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoSchemaDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import org.apache.gravitino.Catalog;
import org.apache.gravitino.Schema;
import org.apache.gravitino.SupportsSchemas;
import org.apache.gravitino.client.GravitinoAdminClient;
import org.apache.gravitino.client.GravitinoMetalake;
import org.apache.gravitino.dto.CatalogDTO;
import org.apache.gravitino.file.FilesetCatalog;
import org.apache.gravitino.messaging.TopicCatalog;
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
        return PageUtil.buildPage(param, list);
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
        String[] strings = schemas.listSchemas();
        Schema schema = schemas.loadSchema("schema");

        TableCatalog tableCatalog = catalog.asTableCatalog();
        TopicCatalog topicCatalog = catalog.asTopicCatalog();
        FilesetCatalog filesetCatalog = catalog.asFilesetCatalog();

        switch (catalog.type()) {
            case RELATIONAL:
                break;
            case MESSAGING:
                break;
            case FILESET:
                break;
            default:
                return Collections.emptyList();
        }
        return null;
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
