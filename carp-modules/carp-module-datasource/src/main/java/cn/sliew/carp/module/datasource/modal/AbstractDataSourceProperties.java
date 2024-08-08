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

package cn.sliew.carp.module.datasource.modal;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.framework.common.jackson.polymorphic.Polymorphic;
import cn.sliew.carp.framework.common.jackson.polymorphic.PolymorphicResolver;
import cn.sliew.carp.module.datasource.modal.file.*;
import cn.sliew.carp.module.datasource.modal.jdbc.*;
import cn.sliew.carp.module.datasource.modal.mq.DataHubDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.mq.KafkaDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.mq.PulsarDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.nosql.CassandraDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.nosql.ElasticsearchDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.nosql.MongoDBDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.nosql.RedisDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.olap.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Data;

@Data
@JsonTypeIdResolver(AbstractDataSourceProperties.DataSourceResolver.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDataSourceProperties implements Polymorphic<DataSourceType> {

    public abstract DataSourceType getType();

    public static final class DataSourceResolver extends PolymorphicResolver<DataSourceType> {
        public DataSourceResolver() {
            bindDefault(MySQLDataSourceProperties.class);

            bind(DataSourceType.MYSQL, MySQLDataSourceProperties.class);
            bind(DataSourceType.ORACLE, OracleDataSourceProperties.class);
            bind(DataSourceType.POSTGRESQL, PostgreSQLDataSourceProperties.class);
            bind(DataSourceType.SQLSERVER, SQLServerDataSourceProperties.class);
            bind(DataSourceType.DMDB, DmDBDataSourceProperties.class);
            bind(DataSourceType.GBASE8A, GBase8aDataSourceProperties.class);
            bind(DataSourceType.GREENPLUM, GreenplumDataSourceProperties.class);
            bind(DataSourceType.PHOENIX, PhoenixDataSourceProperties.class);

            bind(DataSourceType.REDIS, RedisDataSourceProperties.class);
            bind(DataSourceType.ELASTICSEARCH, ElasticsearchDataSourceProperties.class);
            bind(DataSourceType.MONGODB, MongoDBDataSourceProperties.class);
            bind(DataSourceType.CASSANDRA, CassandraDataSourceProperties.class);

            bind(DataSourceType.KAFKA, KafkaDataSourceProperties.class);
            bind(DataSourceType.PULSAR, PulsarDataSourceProperties.class);
            bind(DataSourceType.DATAHUB, DataHubDataSourceProperties.class);

            bind(DataSourceType.FTP, FtpDataSourceProperties.class);
            bind(DataSourceType.SFTP, SftpDataSourceProperties.class);
            bind(DataSourceType.OSS, OSSDataSourceProperties.class);
            bind(DataSourceType.OSSJINDO, OSSJindoDataSourceProperties.class);
            bind(DataSourceType.S3, S3DataSourceProperties.class);
            bind(DataSourceType.HDFS, HDFSDataSourceProperties.class);

            bind(DataSourceType.HIVE, HiveDataSourceProperties.class);

            bind(DataSourceType.CLICKHOUSE, ClickHouseDataSourceProperties.class);
            bind(DataSourceType.KUDU, KuduDataSourceProperties.class);
            bind(DataSourceType.DORIS, DorisDataSourceProperties.class);
            bind(DataSourceType.STARROCKS, StarRocksDataSourceProperties.class);
            bind(DataSourceType.MAXCOMPUTE, MaxComputeDataSourceProperties.class);

            bind(DataSourceType.IOTDB, IoTDBDataSourceProperties.class);
            bind(DataSourceType.NEO4J, Neo4jDataSourceProperties.class);

            bind(DataSourceType.SOCKET, SocketDataSourceProperties.class);
            bind(DataSourceType.HTTP, HttpDataSourceProperties.class);
            bind(DataSourceType.INFLUXDB, InfluxDBDataSourceProperties.class);
        }

        @Override
        protected String typeFromSubtype(Object obj) {
            return subTypes.inverse().get(obj.getClass()).getValue();
        }

        @Override
        protected Class<?> subTypeFromType(String id) {
            Class<?> subType = subTypes.get(DataSourceType.of(id));
            return subType != null ? subType : defaultClass;
        }
    }
}
