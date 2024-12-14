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

package cn.sliew.carp.plguin.jdbc.api;

import cn.sliew.carp.plguin.jdbc.api.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SqlMeta {

    List<JdbcCatalog> getCatalogs(Connection connection) throws SQLException;

    List<JdbcSchema> getSchemas(Connection connection) throws SQLException;

    List<JdbcSchema> getSchemas(Connection connection, String catalog) throws SQLException;

    JdbcSchema getSchema(Connection connection, String catalog, String schema) throws SQLException;

    List<JdbcTable> getTables(Connection connection, String catalog, String schema) throws SQLException;

    JdbcTable getTable(Connection connection, String catalog, String schema, String table) throws SQLException;

    List<JdbcColumn> getColumns(Connection connection, String catalog, String schema, String table) throws SQLException;

    JdbcColumn getColumn(Connection connection, String catalog, String schema, String table, String column) throws SQLException;

    List<JdbcIndexInfo> getIndexInfos(Connection connection, String catalog, String schema, String table) throws SQLException;

    List<JdbcFunction> getFunctions(Connection connection, String catalog, String schema) throws SQLException;

    List<JdbcProcedure> getProcedure(Connection connection, String catalog, String schema) throws SQLException;

}
