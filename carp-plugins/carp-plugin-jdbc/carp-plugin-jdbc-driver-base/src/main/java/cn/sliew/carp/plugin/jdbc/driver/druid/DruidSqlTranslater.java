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

package cn.sliew.carp.plugin.jdbc.driver.druid;

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;
import cn.sliew.carp.plguin.jdbc.api.SqlTranslater;
import com.alibaba.druid.sql.SQLUtils;

public class DruidSqlTranslater implements SqlTranslater {

    @Override
    public String translate(String sql, CarpDataSourceType source, CarpDataSourceType target) {
        switch (source) {
            case ORACLE:
                return translateOracle(sql, target);
            default:
                throw new UnsupportedOperationException("only support translating oracle to mysql");
        }
    }

    private String translateOracle(String sql, CarpDataSourceType target) {
        switch (target) {
            case MYSQL:
                return SQLUtils.translateOracleToMySql(sql);
            default:
                throw new UnsupportedOperationException("only support translating oracle to mysql");
        }
    }
}
