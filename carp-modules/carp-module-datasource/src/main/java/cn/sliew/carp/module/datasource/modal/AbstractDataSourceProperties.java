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
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@JsonTypeIdResolver(AbstractDataSourceProperties.DataSourceResolver.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDataSourceProperties implements Polymorphic<String> {

    public abstract String getType();

    public static final class DataSourceResolver extends PolymorphicResolver<String> {
        public DataSourceResolver() {
            bindDefault(MySQLDataSourceProperties.class);

            bind(DataSourceType.MYSQL.getValue(), MySQLDataSourceProperties.class);
            bind(DataSourceType.ORACLE.getValue(), OracleDataSourceProperties.class);
            bind(DataSourceType.POSTGRESQL.getValue(), PostgreSQLDataSourceProperties.class);
            bind(DataSourceType.SQLSERVER.getValue(), SQLServerDataSourceProperties.class);
            bind(DataSourceType.DMDB.getValue(), DmDBDataSourceProperties.class);
            bind(DataSourceType.GBASE8A.getValue(), GBase8aDataSourceProperties.class);
            bind(DataSourceType.GREENPLUM.getValue(), GreenplumDataSourceProperties.class);
            bind(DataSourceType.PHOENIX.getValue(), PhoenixDataSourceProperties.class);

            bind(DataSourceType.REDIS.getValue(), RedisDataSourceProperties.class);
            bind(DataSourceType.ELASTICSEARCH.getValue(), ElasticsearchDataSourceProperties.class);
            bind(DataSourceType.MONGODB.getValue(), MongoDBDataSourceProperties.class);
            bind(DataSourceType.CASSANDRA.getValue(), CassandraDataSourceProperties.class);

            bind(DataSourceType.KAFKA.getValue(), KafkaDataSourceProperties.class);
            bind(DataSourceType.PULSAR.getValue(), PulsarDataSourceProperties.class);
            bind(DataSourceType.DATAHUB.getValue(), DataHubDataSourceProperties.class);

            bind(DataSourceType.FTP.getValue(), FtpDataSourceProperties.class);
            bind(DataSourceType.SFTP.getValue(), SftpDataSourceProperties.class);
            bind(DataSourceType.OSS.getValue(), OSSDataSourceProperties.class);
            bind(DataSourceType.OSSJINDO.getValue(), OSSJindoDataSourceProperties.class);
            bind(DataSourceType.S3.getValue(), S3DataSourceProperties.class);
            bind(DataSourceType.HDFS.getValue(), HDFSDataSourceProperties.class);

            bind(DataSourceType.HIVE.getValue(), HiveDataSourceProperties.class);

            bind(DataSourceType.CLICKHOUSE.getValue(), ClickHouseDataSourceProperties.class);
            bind(DataSourceType.KUDU.getValue(), KuduDataSourceProperties.class);
            bind(DataSourceType.DORIS.getValue(), DorisDataSourceProperties.class);
            bind(DataSourceType.STARROCKS.getValue(), StarRocksDataSourceProperties.class);
            bind(DataSourceType.MAXCOMPUTE.getValue(), MaxComputeDataSourceProperties.class);

            bind(DataSourceType.IOTDB.getValue(), IoTDBDataSourceProperties.class);
            bind(DataSourceType.NEO4J.getValue(), Neo4jDataSourceProperties.class);

            bind(DataSourceType.SOCKET.getValue(), SocketDataSourceProperties.class);
            bind(DataSourceType.HTTP.getValue(), HttpDataSourceProperties.class);
            bind(DataSourceType.INFLUXDB.getValue(), InfluxDBDataSourceProperties.class);
        }

        @Override
        protected String typeFromSubtype(Object obj) {
            return subTypes.inverse().get(obj.getClass());
        }

        @Override
        protected Class<?> subTypeFromType(String id) {
            Class<?> subType = subTypes.get(DataSourceType.of(id));
            return subType != null ? subType : defaultClass;
        }
    }
}
