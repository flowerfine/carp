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
package cn.sliew.carp.module.dataservice.domain.dao.mybatis;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.config.CarpMybatisConfig;
import cn.sliew.carp.framework.spring.util.PageUtil;
import cn.sliew.carp.module.dataservice.domain.dao.DaoExecutor;
import cn.sliew.carp.module.dataservice.domain.dao.mybatis.entity.MybatisDynamicParamDTO;
import cn.sliew.carp.module.dataservice.domain.dao.mybatis.entity.ParamType;
import cn.sliew.carp.module.dataservice.domain.dao.mybatis.mapper.MybatisMapper;
import cn.sliew.carp.module.dataservice.domain.dao.mybatis.mapper.SqlWrapperProvider;
import cn.sliew.carp.module.datasource.modal.AbstractDataSourceProperties;
import cn.sliew.carp.module.datasource.modal.jdbc.MySQLDataSourceProperties;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.milky.common.exception.Rethrower;
import cn.sliew.milky.common.util.JacksonUtil;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toList;

@Component
public class MybatisDaoExecutor implements DaoExecutor {

    private ConcurrentMap<Long, SqlSessionFactory> configurationRegistry = new ConcurrentHashMap<>();
    private MybatisConfiguration defaultConfiguration = new MybatisConfiguration();

    private SqlSessionFactory getSqlSessionFactory(DsInfoDTO dsInfoDTO) {
        return configurationRegistry.computeIfAbsent(dsInfoDTO.getId(), k -> {
            try {
                AbstractDataSourceProperties dataSourceProperties = JacksonUtil.toObject(JacksonUtil.toJsonNode(dsInfoDTO.getProps()), AbstractDataSourceProperties.class);
                MySQLDataSourceProperties mySQLDataSourceProperties = (MySQLDataSourceProperties) dataSourceProperties;

                HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
                dataSource.setPoolName(dsInfoDTO.getName());
                dataSource.setDriverClassName(mySQLDataSourceProperties.getDriverClassName());
                dataSource.setJdbcUrl(mySQLDataSourceProperties.getUrl());
                dataSource.setUsername(mySQLDataSourceProperties.getUser());
                dataSource.setPassword(mySQLDataSourceProperties.getPassword());
                dataSource.setMinimumIdle(1);
                dataSource.setMaximumPoolSize(5);

                MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
                GlobalConfig globalConfig = GlobalConfigUtils.defaults();
                globalConfig.setMetaObjectHandler(new CarpMybatisConfig.CarpMetaHandler());

                MybatisPlusProperties props = new MybatisPlusProperties();
                props.setMapperLocations(new String[]{DataSourceConstants.MAPPER_XML_PATH});
                factoryBean.setMapperLocations(props.resolveMapperLocations());

                MybatisConfiguration configuration = new MybatisConfiguration();
                configuration.addMapper(MybatisMapper.class);
                configuration.addMapper(SqlWrapperProvider.class);
                configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
                configuration.setMapUnderscoreToCamelCase(true);
                configuration.setLogImpl(Slf4jImpl.class);
                factoryBean.setConfiguration(configuration);
                factoryBean.setGlobalConfig(globalConfig);
                factoryBean.setDataSource(dataSource);
                return factoryBean.getObject();
            } catch (Exception e) {
                Rethrower.throwAs(e);
                return null;
            }
        });
    }

    private Configuration getConfiguration(DsInfoDTO dsInfoDTO) {
        return getSqlSessionFactory(dsInfoDTO).getConfiguration();
    }

    @Override
    public PageResult<String> page(DsInfoDTO dsInfoDTO, PageParam param) {
        return PageUtil.buildPage(param, listAll(dsInfoDTO));
    }

    @Override
    public List<String> listAll(DsInfoDTO dsInfoDTO) {
        return getConfiguration(dsInfoDTO).getMappedStatementNames().stream().toList();
    }

    @Override
    public void register(String id, String sqlScript, DsInfoDTO dsInfoDTO) {
        getConfiguration(dsInfoDTO).addMappedStatement(buildMappedStatement(id, sqlScript));
    }

    @Override
    public void unregister(String id, DsInfoDTO dsInfoDTO) {
        Map<String, MappedStatement> mappedStatements = readField(getConfiguration(dsInfoDTO), "mappedStatements");
        mappedStatements.remove(id);
    }

    @Override
    public List parseParams(String sqlScript) {
        MappedStatement mappedStatement = buildMappedStatement(UUIDUtil.randomUUId(), sqlScript);
        SqlSource sqlSource = mappedStatement.getSqlSource();
        List<MybatisDynamicParamDTO> dynamicParamDTOS;
        if (sqlSource instanceof RawSqlSource) {
            dynamicParamDTOS = parseRawSqlSourceParam((RawSqlSource) sqlSource);
        } else if (sqlSource instanceof DynamicSqlSource) {
            dynamicParamDTOS = parseDynamicSqlSourceParam((DynamicSqlSource) sqlSource);
        } else {
            throw new IllegalStateException("mybatis mappedstatement type error: " + sqlScript);
        }
        Map<String, List<MybatisDynamicParamDTO>> collect = dynamicParamDTOS.stream()
                .filter(paramDTO -> paramDTO.getParamType() == ParamType.PLACEHOLDER_COMPILE
                        || paramDTO.getParamType() == ParamType.PLACEHOLDER_REPLACE
                        || paramDTO.getParamType() == ParamType.VARIABLE_TEST)
                .collect(Collectors.groupingBy(MybatisDynamicParamDTO::getName));
        return collect.values().stream().map(list -> list.get(0)).collect(toList());
    }


    private List<MybatisDynamicParamDTO> parseRawSqlSourceParam(RawSqlSource sqlSource) {
        StaticSqlSource staticSqlSource = readField(sqlSource, "sqlSource");
        List<ParameterMapping> parameterMappings = readField(staticSqlSource, "parameterMappings");
        return parameterMappings.stream()
                .map(parameterMapping -> {
                    MybatisDynamicParamDTO dto = new MybatisDynamicParamDTO();
                    dto.setName(parameterMapping.getProperty());
                    dto.setSource(null);
                    dto.setRequired(true);
                    dto.setParamType(ParamType.PLACEHOLDER_COMPILE);
                    return dto;
                })
                .collect(toList());
    }

    private List<MybatisDynamicParamDTO> parseDynamicSqlSourceParam(DynamicSqlSource sqlSource) {
        SqlNode sqlNode = readField(sqlSource, "rootSqlNode");
        return parseSqlNode(sqlNode, true);
    }

    private List<MybatisDynamicParamDTO> parseSqlNode(SqlNode sqlNode, boolean required) {
        if (sqlNode instanceof MixedSqlNode) {
            return parseMixedSqlNodeParam((MixedSqlNode) sqlNode, required);
        }
        if (sqlNode instanceof TextSqlNode) {
            return parseTextSqlNodeParam((TextSqlNode) sqlNode, required);
        }
        if (sqlNode instanceof StaticTextSqlNode) {
            return parseStaticTextSqlNodeParam((StaticTextSqlNode) sqlNode, required);
        }
        if (sqlNode instanceof IfSqlNode) {
            return parseIfSqlNodeParam((IfSqlNode) sqlNode);
        }
        if (sqlNode instanceof ForEachSqlNode) {
            return parseForEachSqlNodeParam((ForEachSqlNode) sqlNode, required);
        }
        if (sqlNode instanceof ChooseSqlNode) {
            return parseChooseSqlNodeParam((ChooseSqlNode) sqlNode);
        }
        if (sqlNode instanceof TrimSqlNode) {
            return parseTrimSqlNodeParam((TrimSqlNode) sqlNode, required);
        }
        if (sqlNode instanceof VarDeclSqlNode) {
            return parseVarDeclSqlNodeParam((VarDeclSqlNode) sqlNode, required);
        }
        return Collections.emptyList();
    }

    private List<MybatisDynamicParamDTO> parseMixedSqlNodeParam(MixedSqlNode sqlNode, boolean required) {
        List<SqlNode> contents = readField(sqlNode, "contents");
        return contents.stream()
                .map(node -> parseSqlNode(node, required))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private List<MybatisDynamicParamDTO> parseTextSqlNodeParam(TextSqlNode sqlNode, boolean required) {
        List<MybatisDynamicParamDTO> all = new ArrayList<>();

        TextSqlNodeTokenHandler handler1 = new TextSqlNodeTokenHandler();
        GenericTokenParser parser1 = new GenericTokenParser("${", "}", handler1);
        parser1.parse(readField(sqlNode, "text"));
        // TODO mybatis允许在大括号内标记类型，所以可以从大括号内尝试获取类型
        all.addAll(handler1.getParamSet().stream()
                .map(param -> new MybatisDynamicParamDTO(param, ParamType.PLACEHOLDER_REPLACE, null, required))
                .collect(toList()));


        TextSqlNodeTokenHandler handler2 = new TextSqlNodeTokenHandler();
        GenericTokenParser parser2 = new GenericTokenParser("#{", "}", handler2);
        parser2.parse(readField(sqlNode, "text"));
        // TODO mybatis允许在大括号内标记类型，所以可以从大括号内尝试获取类型
        all.addAll(handler2.getParamSet().stream()
                .map(param -> new MybatisDynamicParamDTO(param, ParamType.PLACEHOLDER_COMPILE, null, required))
                .collect(toList()));
        return all;
    }

    private List<MybatisDynamicParamDTO> parseStaticTextSqlNodeParam(StaticTextSqlNode sqlNode, boolean required) {
        TextSqlNodeTokenHandler handler = new TextSqlNodeTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        parser.parse(readField(sqlNode, "text"));
        // TODO mybatis允许在大括号内标记类型，所以可以从大括号内尝试获取类型
        return handler.getParamSet().stream()
                .map(param -> new MybatisDynamicParamDTO(param, ParamType.PLACEHOLDER_COMPILE, null, required))
                .collect(toList());
    }

    private List<MybatisDynamicParamDTO> parseIfSqlNodeParam(IfSqlNode sqlNode) {
        List<MybatisDynamicParamDTO> dynamicParamDTOS = new ArrayList<>();

        // test 表达式
        String test = readField(sqlNode, "test");
        dynamicParamDTOS.addAll(parseTestExpression(test));

        // 标签内部
        SqlNode contents = readField(sqlNode, "contents");
        dynamicParamDTOS.addAll(parseSqlNode(contents, false));
        return dynamicParamDTOS;
    }

    private List<MybatisDynamicParamDTO> parseForEachSqlNodeParam(ForEachSqlNode sqlNode, boolean required) {
        List<MybatisDynamicParamDTO> forEachParamList = new ArrayList<>();

        // TODO collectionExpression 支持表达式，但大多数情况都只会传入变量
        String collectionExpression = readField(sqlNode, "collectionExpression");
        forEachParamList.add(new MybatisDynamicParamDTO(collectionExpression, ParamType.PLACEHOLDER_COMPILE, null, required));

        String item = readField(sqlNode, "item");
        if (item != null) {
            forEachParamList.add(new MybatisDynamicParamDTO(item, ParamType.VARIABLE_FOREACH, collectionExpression, false));
        }
        String index = readField(sqlNode, "index");
        if (index != null) {
            forEachParamList.add(new MybatisDynamicParamDTO(index, ParamType.VARIABLE_FOREACH, collectionExpression, false));
        }

        return forEachParamList;
    }

    private List<MybatisDynamicParamDTO> parseChooseSqlNodeParam(ChooseSqlNode sqlNode) {
        List<MybatisDynamicParamDTO> chooseParamList = new ArrayList<>();

        List<SqlNode> ifSqlNodes = readField(sqlNode, "ifSqlNodes");
        ifSqlNodes.forEach(content -> chooseParamList.addAll(parseSqlNode(content, false)));

        SqlNode defaultSqlNode = readField(sqlNode, "defaultSqlNode");
        if (defaultSqlNode != null) {
            chooseParamList.addAll(parseSqlNode(defaultSqlNode, false));
        }
        return chooseParamList;
    }

    private List<MybatisDynamicParamDTO> parseTrimSqlNodeParam(TrimSqlNode sqlNode, boolean required) {
        SqlNode contents = readField(TrimSqlNode.class, sqlNode, "contents");
        return parseSqlNode(contents, required);
    }

    private List<MybatisDynamicParamDTO> parseVarDeclSqlNodeParam(VarDeclSqlNode sqlNode, boolean required) {
        String name = readField(sqlNode, "name");
        String expression = readField(sqlNode, "expression");
        return Collections.singletonList(new MybatisDynamicParamDTO(name, ParamType.VARIABLE_BIND, expression, required));
    }

    private List<MybatisDynamicParamDTO> parseTestExpression(String test) {
        List<MybatisDynamicParamDTO> dynamicParamDTOS = new ArrayList<>();
        String regex = "(\\w+)\\s*(?:!=|>=|<=|==|<|>|&lt;|&lt;=|&gt;|&gt;=)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()) {
            String field = matcher.group(1);
            dynamicParamDTOS.add(new MybatisDynamicParamDTO(field, ParamType.VARIABLE_TEST, null, true));
        }
        return dynamicParamDTOS;
    }

    @Override
    public String parseSql(String id, String sqlScript, Map<String, Object> params) {
        MappedStatement mappedStatement = buildMappedStatement(id, sqlScript);
        return mappedStatement.getBoundSql(params).getSql();
    }

    @Override
    public Object execute(String id, String sqlScript, Map<String, Object> params, DsInfoDTO dsInfoDTO) {
        Configuration configuration = getConfiguration(dsInfoDTO);
        checkState(configuration.hasStatement(id), "MappedStatement not found for id: " + id);
        return doExecute(dsInfoDTO, jdbcExecutor -> jdbcExecutor.selectOne(dsInfoDTO, sqlScript, params));
    }

    private Object doExecute(DsInfoDTO dsInfoDTO, Function<JdbcExecutor, Object> function) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(dsInfoDTO);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            MybatisMapper mapper = sqlSession.getMapper(MybatisMapper.class);
            JdbcExecutor jdbcExecutor = new JdbcExecutor(mapper);
            return function.apply(jdbcExecutor);
        } catch (Exception e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    private MappedStatement buildMappedStatement(String id, String sqlScript) {
        LanguageDriver languageDriver = defaultConfiguration.getDefaultScriptingLanguageInstance();
        SqlSource sqlSource = languageDriver.createSqlSource(defaultConfiguration, sqlScript, null);
        // fixme sql 类型 select, insert, update, delete
        MappedStatement.Builder builder = new MappedStatement.Builder(defaultConfiguration, id, sqlSource, SqlCommandType.UNKNOWN);
        return builder.build();
    }

    private <T> T readField(Object object, String fieldName) {
        try {
            return (T) FieldUtils.readDeclaredField(object, fieldName, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T readField(Class clazz, Object object, String fieldName) {
        try {
            Field field = FieldUtils.getDeclaredField(clazz, fieldName, true);
            return (T) FieldUtils.readField(field, object, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    static class TextSqlNodeTokenHandler implements TokenHandler {

        private Set<String> paramSet = new HashSet<String>();

        public Set<String> getParamSet() {
            return paramSet;
        }

        @Override
        public String handleToken(String content) {
            paramSet.add(content);
            return content;
        }
    }
}
