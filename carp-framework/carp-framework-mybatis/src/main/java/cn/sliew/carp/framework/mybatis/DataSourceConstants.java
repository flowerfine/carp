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

    public static final String MAPPER_FRAMEWORK_DAG_PACKAGE = "cn.sliew.carp.framework.dag.repository.mapper";
    public static final String MAPPER_MODULE_DATASOURCE_PACKAGE = "cn.sliew.carp.module.datasource.repository.mapper";
    public static final String MAPPER_MODULE_KUBERNETES_PACKAGE = "cn.sliew.carp.module.kubernetes.repository.mapper";
    public static final String MAPPER_MODULE_PLUGIN_PACKAGE = "cn.sliew.carp.module.plugin.repository.mapper";
    public static final String MAPPER_MODULE_SCHEDULER_PACKAGE = "cn.sliew.carp.module.scheduler.repository.mapper";
    public static final String MAPPER_MODULE_SECURITY_PACKAGE = "cn.sliew.carp.module.security.core.repository.mapper";

    public static final String MAPPER_XML_PATH = "classpath*:cn/sliew/carp/**/repository/**/*.xml";

    public static final String SQL_SESSION_FACTORY = "carpSqlSessionFactory";
    public static final String DATA_SOURCE_FACTORY = "carpDataSource";
    public static final String TRANSACTION_MANAGER_FACTORY = "carpTransactionManager";
}
