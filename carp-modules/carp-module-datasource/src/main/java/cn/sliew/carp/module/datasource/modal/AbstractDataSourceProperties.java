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

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;
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
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@JsonTypeIdResolver(AbstractDataSourceProperties.DataSourceResolver.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDataSourceProperties implements Polymorphic<String> {

    @Override
    public abstract String getType();

    public static final class DataSourceResolver extends PolymorphicResolver<CarpDataSourceType> {
        public DataSourceResolver() {
            bindDefault(MySQLDataSourceProperties.class);

            bind(CarpDataSourceType.MYSQL, MySQLDataSourceProperties.class);
            bind(CarpDataSourceType.ORACLE, OracleDataSourceProperties.class);
            bind(CarpDataSourceType.POSTGRESQL, PostgreSQLDataSourceProperties.class);
            bind(CarpDataSourceType.SQLSERVER, SQLServerDataSourceProperties.class);
            bind(CarpDataSourceType.DMDB, DmDBDataSourceProperties.class);
            bind(CarpDataSourceType.GBASE8A, GBase8aDataSourceProperties.class);
            bind(CarpDataSourceType.GREENPLUM, GreenplumDataSourceProperties.class);
            bind(CarpDataSourceType.PHOENIX, PhoenixDataSourceProperties.class);

            bind(CarpDataSourceType.REDIS, RedisDataSourceProperties.class);
            bind(CarpDataSourceType.ELASTICSEARCH, ElasticsearchDataSourceProperties.class);
            bind(CarpDataSourceType.MONGODB, MongoDBDataSourceProperties.class);
            bind(CarpDataSourceType.CASSANDRA, CassandraDataSourceProperties.class);

            bind(CarpDataSourceType.KAFKA, KafkaDataSourceProperties.class);
            bind(CarpDataSourceType.PULSAR, PulsarDataSourceProperties.class);
            bind(CarpDataSourceType.DATAHUB, DataHubDataSourceProperties.class);

            bind(CarpDataSourceType.FTP, FtpDataSourceProperties.class);
            bind(CarpDataSourceType.SFTP, SftpDataSourceProperties.class);
            bind(CarpDataSourceType.OSS, OSSDataSourceProperties.class);
            bind(CarpDataSourceType.OSSJINDO, OSSJindoDataSourceProperties.class);
            bind(CarpDataSourceType.S3, S3DataSourceProperties.class);
            bind(CarpDataSourceType.HDFS, HDFSDataSourceProperties.class);

            bind(CarpDataSourceType.HIVE, HiveDataSourceProperties.class);

            bind(CarpDataSourceType.CLICKHOUSE, ClickHouseDataSourceProperties.class);
            bind(CarpDataSourceType.KUDU, KuduDataSourceProperties.class);
            bind(CarpDataSourceType.DORIS, DorisDataSourceProperties.class);
            bind(CarpDataSourceType.STARROCKS, StarRocksDataSourceProperties.class);
            bind(CarpDataSourceType.MAXCOMPUTE, MaxComputeDataSourceProperties.class);

            bind(CarpDataSourceType.IOTDB, IoTDBDataSourceProperties.class);
            bind(CarpDataSourceType.NEO4J, Neo4jDataSourceProperties.class);

            bind(CarpDataSourceType.SOCKET, SocketDataSourceProperties.class);
            bind(CarpDataSourceType.HTTP, HttpDataSourceProperties.class);
            bind(CarpDataSourceType.INFLUXDB, InfluxDBDataSourceProperties.class);
        }

        @Override
        protected String typeFromSubtype(Object obj) {
            return subTypes.inverse().get(obj.getClass()).getValue();
        }

        @Override
        protected Class<?> subTypeFromType(String id) {
            Class<?> subType = subTypes.get(CarpDataSourceType.of(id));
            return subType != null ? subType : defaultClass;
        }
    }
}
