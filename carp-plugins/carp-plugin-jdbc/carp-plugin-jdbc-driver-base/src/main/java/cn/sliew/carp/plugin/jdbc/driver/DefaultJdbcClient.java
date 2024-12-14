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

package cn.sliew.carp.plugin.jdbc.driver;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.module.datasource.modal.jdbc.JdbcDataSourceProperties;
import cn.sliew.carp.plguin.jdbc.api.*;
import cn.sliew.carp.plugin.jdbc.driver.druid.DruidSqlFormatter;
import cn.sliew.carp.plugin.jdbc.driver.druid.DruidSqlTranslater;
import cn.sliew.carp.plugin.jdbc.driver.druid.util.DruidUtil;
import cn.sliew.carp.plugin.jdbc.driver.jooq.JooqSqlMeta;
import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultJdbcClient implements JdbcClient {

    private JdbcDataSourceProperties properties;
    private DruidDataSource dataSource;

    public DefaultJdbcClient(JdbcDataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public void start() throws SQLException {
        this.dataSource = DruidUtil.createDataSource(properties);
    }

    @Override
    public void close() {
        DruidUtil.closeDataSource(dataSource);
    }

    @Override
    public DataSourceType getType() {
        return DataSourceType.of(properties.getType());
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public SqlMeta getSqlMeta() {
        return new JooqSqlMeta();
    }

    @Override
    public SqlExecutor getSqlExecutor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlFormatter getSqlFormatter() {
        return new DruidSqlFormatter();
    }

    @Override
    public SqlTranslater getSqlTranslator() {
        return new DruidSqlTranslater();
    }
}
