package cn.sliew.carp.module.odps.config;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class OdpsDataSourceConfig {

//    public static final String MAPPER_XML_PATH = "classpath*:cn/sliew/carp/odps/repository/**/*.xml";
    public static final String SQL_SESSION_FACTORY = "cn.sliew.carp.module.odps.config.odpsSqlSessionFactory";
    public static final String ODPS_DATA_SOURCE_FACTORY = "cn.sliew.carp.module.odps.config.odpsDataSource";
    public static final String TRANSACTION_MANAGER_FACTORY = "cn.sliew.carp.module.odps.config.odpsTransactionManager";

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Bean(ODPS_DATA_SOURCE_FACTORY)
    @ConfigurationProperties(prefix = "spring.datasource.odps")
    public DataSource carpDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .build();
    }

    @Bean(TRANSACTION_MANAGER_FACTORY)
    public DataSourceTransactionManager carpTransactionManager(
            @Qualifier(ODPS_DATA_SOURCE_FACTORY) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory carpSqlSessionFactory(
            @Qualifier(ODPS_DATA_SOURCE_FACTORY) DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        globalConfig.setMetaObjectHandler(new CarpMybatisConfig.CarpMetaHandler());

        MybatisPlusProperties props = new MybatisPlusProperties();
//        props.setMapperLocations(new String[]{MAPPER_XML_PATH});
        factoryBean.setMapperLocations(props.resolveMapperLocations());

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);
        factoryBean.setConfiguration(configuration);
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setDataSource(dataSource);
        factoryBean.setPlugins(mybatisPlusInterceptor);
        return factoryBean.getObject();
    }

}
