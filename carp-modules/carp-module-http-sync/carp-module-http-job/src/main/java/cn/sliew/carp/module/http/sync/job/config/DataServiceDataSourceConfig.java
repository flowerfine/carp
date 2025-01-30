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
package cn.sliew.carp.module.http.sync.job.config;

import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.config.CarpMybatisConfig;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(sqlSessionFactoryRef = DataServiceDataSourceConfig.SQL_SESSION_FACTORY,
        basePackages = {DataServiceDataSourceConfig.MAPPER_MODULE_HTTP_SYNC_JOB_PACKAGE})
public class DataServiceDataSourceConfig {

    public static final String MAPPER_MODULE_HTTP_SYNC_JOB_PACKAGE = "cn.sliew.carp.module.http.sync.job.repository.mapper";

    public static final String SQL_SESSION_FACTORY = "dataServiceSqlSessionFactory";
    public static final String DATA_SOURCE_FACTORY = "dataServiceDataSource";
    public static final String TRANSACTION_MANAGER_FACTORY = "dataServiceTransactionManager";

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Primary
    @Bean(DataServiceDataSourceConfig.DATA_SOURCE_FACTORY)
    @ConfigurationProperties(prefix = "spring.datasource.dataservice")
    public DataSource dataServiceDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(DataServiceDataSourceConfig.TRANSACTION_MANAGER_FACTORY)
    public DataSourceTransactionManager dataServiceTransactionManager() {
        return new DataSourceTransactionManager(dataServiceDataSource());
    }

    @Primary
    @Bean(DataServiceDataSourceConfig.SQL_SESSION_FACTORY)
    public SqlSessionFactory dataServiceSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        globalConfig.setMetaObjectHandler(new CarpMybatisConfig.CarpMetaHandler());

        MybatisPlusProperties props = new MybatisPlusProperties();
        props.setMapperLocations(new String[]{DataSourceConstants.MAPPER_XML_PATH});
        factoryBean.setMapperLocations(props.resolveMapperLocations());

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);
        factoryBean.setConfiguration(configuration);
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setDataSource(dataServiceDataSource());
        factoryBean.setPlugins(mybatisPlusInterceptor);
        return factoryBean.getObject();
    }

}
