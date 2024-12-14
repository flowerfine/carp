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
 *
 */

package cn.sliew.carp.plugin.jdbc.driver.jooq;

import cn.sliew.carp.plguin.jdbc.api.SqlMeta;
import cn.sliew.carp.plguin.jdbc.api.model.*;
import cn.sliew.carp.plguin.jdbc.api.model.enums.TableType;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * todo jackson + jdbc driver
 */
public class JooqSqlMeta implements SqlMeta {

    private <T> List<T> convert(Connection connection, ResultSet rs, Class<T> clazz) {
        return DSL.using(connection)
                .fetch(rs)
                .intoMaps()
                .stream()
                .map(map -> {
                    JsonNode jsonNode = JacksonUtil.toJsonNode(map);
                    return JacksonUtil.toObject(jsonNode, clazz);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<JdbcCatalog> getCatalogs(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet catalogs = metaData.getCatalogs();
        return convert(connection, catalogs, JdbcCatalog.class);
    }

    @Override
    public List<JdbcSchema> getSchemas(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas();
        return convert(connection, schemas, JdbcSchema.class);
    }

    @Override
    public List<JdbcSchema> getSchemas(Connection connection, String catalog) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas(catalog, null);
        return convert(connection, schemas, JdbcSchema.class);
    }

    @Override
    public JdbcSchema getSchema(Connection connection, String catalog, String schema) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas(catalog, schema);
        return convert(connection, schemas, JdbcSchema.class).get(0);
    }

    @Override
    public List<JdbcTable> getTables(Connection connection, String catalog, String schema) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(catalog, schema, null, new String[]{TableType.TABLE.getTypeName(), TableType.SYSTEM_TABLE.getTypeName(), TableType.VIEW.getTypeName()});
        return convert(connection, tables, JdbcTable.class);
    }

    @Override
    public JdbcTable getTable(Connection connection, String catalog, String schema, String table) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(catalog, schema, table, new String[]{TableType.TABLE.getTypeName(), TableType.SYSTEM_TABLE.getTypeName(), TableType.VIEW.getTypeName()});
        return convert(connection, tables, JdbcTable.class).get(0);
    }

    @Override
    public List<JdbcColumn> getColumns(Connection connection, String catalog, String schema, String table) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(catalog, schema, table, null);
        return convert(connection, columns, JdbcColumn.class);
    }

    @Override
    public JdbcColumn getColumn(Connection connection, String catalog, String schema, String table, String column) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(catalog, schema, table, column);
        return convert(connection, columns, JdbcColumn.class).get(0);
    }

    @Override
    public List<JdbcIndexInfo> getIndexInfos(Connection connection, String catalog, String schema, String table) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet indexInfo = metaData.getIndexInfo(catalog, schema, table, false, false);
        return convert(connection, indexInfo, JdbcIndexInfo.class);
    }

    @Override
    public List<JdbcFunction> getFunctions(Connection connection, String catalog, String schema) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet functions = metaData.getFunctions(catalog, schema, null);
        return convert(connection, functions, JdbcFunction.class);
    }

    @Override
    public List<JdbcProcedure> getProcedure(Connection connection, String catalog, String schema) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet procedures = metaData.getProcedures(catalog, schema, null);
        return convert(connection, procedures, JdbcProcedure.class);
    }
}
