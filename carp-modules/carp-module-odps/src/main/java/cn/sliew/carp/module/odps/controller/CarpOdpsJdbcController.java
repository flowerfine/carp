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
package cn.sliew.carp.module.odps.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.odps.config.OdpsDataSourceConfig;
import cn.sliew.carp.module.odps.vo.ResultSetVO;
import cn.sliew.milky.common.exception.Rethrower;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/odps/jdbc")
@Tag(name = "ODPS管理-JDBC管理")
public class CarpOdpsJdbcController {

    @Autowired
    @Qualifier(OdpsDataSourceConfig.SQL_SESSION_FACTORY)
    private SqlSessionFactory sqlSessionFactory;

    @GetMapping("catalogs")
    @Operation(summary = "查询-Catalog列表", description = "查询-Catalog列表，也是 project")
    public ResultSetVO listCatalogs() throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            Connection connection = session.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getCatalogs();
            return new ResultSetVO(rs, false);
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    @GetMapping("schemas")
    @Operation(summary = "查询-Schema列表", description = "查询-Schema列表")
    public ResultSetVO listSchemas(@RequestParam("catalog") String catalog) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            Connection connection = session.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getSchemas(catalog, null);
            return new ResultSetVO(rs, false);
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    @GetMapping("tables")
    @Operation(summary = "查询-Table列表", description = "查询-Table列表")
    public ResultSetVO listTables(@RequestParam("catalog") String catalog, @RequestParam("schema") String schema) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            Connection connection = session.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(catalog, schema,null, new String[]{"TABLE", "VIEW"});
            return new ResultSetVO(rs, false);
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }
}
