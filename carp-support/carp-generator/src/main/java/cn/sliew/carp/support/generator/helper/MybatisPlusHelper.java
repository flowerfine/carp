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

package cn.sliew.carp.support.generator.helper;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;

public class MybatisPlusHelper {

    private static final String TABLE_PREFIX = "";

    public static void generatorMySQL(String jdbcUrl, String jdbcUserName, String jdbcPassword, String author, String basePackage, String moduleName, String[] tables) {
        doGenerator(mysqlDataSourceConfig(jdbcUrl, jdbcUserName, jdbcPassword), author, basePackage, moduleName, tables);
    }

    public static void generatorPostgreSQL(String jdbcUrl, String jdbcUserName, String jdbcPassword, String schema, String author, String basePackage, String moduleName, String[] tables) {
        doGenerator(postgresqlDataSourceConfig(jdbcUrl, jdbcUserName, jdbcPassword, schema), author, basePackage, moduleName, tables);
    }

    public static void doGenerator(DataSourceConfig.Builder dataSourceConfigBuilder, String author, String basePackage, String moduleName, String[] tables) {
        //自动生成配置
        FastAutoGenerator generator = FastAutoGenerator.create(dataSourceConfigBuilder)
                .globalConfig(builder -> globalConfig(builder, author))
                .packageConfig(builder -> packageConfig(builder, basePackage, moduleName))
                .templateConfig(MybatisPlusHelper::templateConfig)
                .strategyConfig(builder -> strategyConfig(builder, tables))
                .injectionConfig(MybatisPlusHelper::injectionConfig);
        generator.execute();
    }

    /**
     * 数据源配置
     *
     * @return DataSourceConfig
     */
    private static DataSourceConfig.Builder mysqlDataSourceConfig(String jdbcUrl, String jdbcUserName, String password) {
        return new DataSourceConfig.Builder(jdbcUrl, jdbcUserName, password)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());
    }

    /**
     * 数据源配置
     *
     * @return DataSourceConfig
     */
    private static DataSourceConfig.Builder postgresqlDataSourceConfig(String jdbcUrl, String jdbcUserName, String password, String schema) {
        return new DataSourceConfig.Builder(jdbcUrl, jdbcUserName, password)
                .schema(schema)
                .dbQuery(new PostgreSqlQuery())
                .typeConvert(new PostgreSqlTypeConvert())
                .keyWordsHandler(new PostgreSqlKeyWordsHandler());
    }

    /**
     * 全局配置
     *
     * @return GlobalConfig
     */
    private static void globalConfig(GlobalConfig.Builder builder, String author) {
        builder
                .outputDir(System.getProperty("user.dir") +
                        "/carp-support/carp-generator/src/main/java/")
                .author(author)
                .enableSpringdoc()
                .dateType(DateType.ONLY_DATE)
                .commentDate("yyyy-MM-dd");
    }

    /**
     * 包配置
     *
     * @return PackageConfig
     */
    private static void packageConfig(PackageConfig.Builder builder, String basePackage, String moduleName) {
        builder.parent(basePackage)
                .moduleName(moduleName)
                .entity("dao.entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("dao.mapper")
                .xml("dao.mapper")
                .controller("api.controller");
//                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/wangqi/Downloads/generator"));
    }

    private static void templateConfig(TemplateConfig.Builder builder) {
        // 设置 null 避免对应的类生成
        // 修改 entity 模板，使用自定义的
        builder.controller(null)
                .service(null)
                .serviceImpl(null)
                .entity("/custom-entity.java.vm");
    }

    /**
     * 策略配置
     *
     * @return StrategyConfig
     */
    private static void strategyConfig(StrategyConfig.Builder builder, String[] tables) {
        builder.enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude(tables)
                .addTablePrefix(TABLE_PREFIX);

        Entity.Builder entityBuilder = builder.entityBuilder();
        entityBuilder.superClass(BaseAuditDO.class)
                .enableLombok()
                .enableTableFieldAnnotation()
                .enableRemoveIsPrefix()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addSuperEntityColumns("id", "creator", "created_time", "editor", "update_time")
                .idType(IdType.AUTO)
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .formatFileName("%s");

        Mapper.Builder mapperBuilder = builder.mapperBuilder();
        mapperBuilder.superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper");

        Service.Builder serviceBuilder = builder.serviceBuilder();
        serviceBuilder.formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImp")
                .build();


        Controller.Builder controllerBuilder = builder.controllerBuilder();
        controllerBuilder.enableHyphenStyle()
                .enableRestStyle()
                .formatFileName("%sController")
                .build();

    }

    /**
     * 自定义配置
     *
     * @return InjectionConfig
     */
    private static void injectionConfig(InjectionConfig.Builder builder) {

    }
}
