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

package cn.sliew.carp.framework.mybatis;

public enum DataSourceConstants {
    ;

    public static final String MASTER_ENTITY_PACKAGE = "cn.sliew.carp.dao.entity.master";
    public static final String MASTER_MAPPER_PACKAGE = "cn.sliew.carp.dao.mapper.master";
    public static final String MASTER_MAPPER_XML_PATH = "classpath*:cn.sliew.carp.dao.mapper/master/**/*.xml";

    public static final String MASTER_SQL_SESSION_FACTORY = "carpMasterSqlSessionFactory";
    public static final String MASTER_DATA_SOURCE_FACTORY = "carpMasterDataSource";
    public static final String MASTER_TRANSACTION_MANAGER_FACTORY = "carpMasterTransactionManager";

    public static final String LOG_ENTITY_PACKAGE = "cn.sliew.carp.dao.entity.log";
    public static final String LOG_MAPPER_PACKAGE = "cn.sliew.carp.dao.mapper.log";
    public static final String LOG_MAPPER_XML_PATH = "classpath*:cn.sliew.carp.dao.mapper/log/**/*.xml";

    public static final String LOG_SQL_SESSION_FACTORY = "carpLogSqlSessionFactory";
    public static final String LOG_DATA_SOURCE_FACTORY = "carpLogDataSource";
    public static final String LOG_TRANSACTION_MANAGER_FACTORY = "carpLogTransactionManager";
}
