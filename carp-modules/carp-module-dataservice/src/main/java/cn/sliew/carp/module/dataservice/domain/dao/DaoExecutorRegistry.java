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
package cn.sliew.carp.module.dataservice.domain.dao;

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;
import cn.sliew.carp.module.dataservice.domain.dao.mybatis.MybatisDaoExecutor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DaoExecutorRegistry implements InitializingBean {

    private ConcurrentMap<CarpDataSourceType, DaoExecutor> registry = new ConcurrentHashMap<>();

    @Autowired
    private MybatisDaoExecutor mybatisDataServiceExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        register(CarpDataSourceType.MYSQL, mybatisDataServiceExecutor);
    }

    public DaoExecutor get(CarpDataSourceType dataSourceType) {
        return registry.get(dataSourceType);
    }

    public void register(CarpDataSourceType dataSourceType, DaoExecutor daoExecutor) {
        registry.put(dataSourceType, daoExecutor);
    }

    public void unregister(CarpDataSourceType dataSourceType) {
        registry.remove(dataSourceType);
    }
}
