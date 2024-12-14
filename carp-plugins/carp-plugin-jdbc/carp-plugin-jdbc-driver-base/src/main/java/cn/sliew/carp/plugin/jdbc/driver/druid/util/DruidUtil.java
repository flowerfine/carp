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

package cn.sliew.carp.plugin.jdbc.driver.druid.util;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.module.datasource.modal.jdbc.JdbcDataSourceProperties;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;
import java.time.Duration;

public enum DruidUtil {
    ;

    public static DruidDataSource createDataSource(JdbcDataSourceProperties properties) throws SQLException {
        DruidDataSource instance = new DruidDataSource();
        instance.setDriverClassName(properties.getDriverClassName());
        instance.setUrl(properties.getUrl());
        instance.setUsername(properties.getUser());
        instance.setPassword(properties.getPassword());
        instance.setMinIdle(1);
        instance.setInitialSize(1);
        instance.setMaxActive(10);
        instance.setAsyncInit(true);
        instance.setMaxWait(Duration.ofSeconds(6L).toMillis());
        instance.setKeepAlive(true);
        instance.setPoolPreparedStatements(true);
        instance.setMaxOpenPreparedStatements(20);
        instance.setValidationQuery("SELECT 1");
        instance.setTestOnBorrow(false);
        instance.setTestOnReturn(false);
        instance.setTestWhileIdle(true);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        instance.setTimeBetweenEvictionRunsMillis(Duration.ofMinutes(1L).toMillis());
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        instance.setMinEvictableIdleTimeMillis(Duration.ofMinutes(5L).toMillis());
        instance.setMaxEvictableIdleTimeMillis(Duration.ofHours(1L).toMillis());

        instance.init();
        return instance;
    }

    public static void closeDataSource(DruidDataSource instance) {
        if (instance != null && instance.isClosed() == false) {
            instance.close();
        }
    }

    public static DbType convert(DataSourceType jdbcEnum) {
        switch (jdbcEnum) {
            case MYSQL:
                return DbType.mysql;
            case POSTGRESQL:
                return DbType.postgresql;
            case ORACLE:
                return DbType.oracle;
            case SQLSERVER:
                return DbType.sqlserver;
            case HIVE:
                return DbType.hive;
            case CLICKHOUSE:
                return DbType.clickhouse;
            case DORIS:
                return DbType.doris;
            case STARROCKS:
                return DbType.starrocks;
            case MAXCOMPUTE:
                return DbType.odps;
            case TIDB:
                return DbType.tidb;
            case ELASTICSEARCH:
                return DbType.elastic_search;
            default:
                return DbType.other;
        }
    }
}
