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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.dataservice.domain.dao.mybatis.mapper.MybatisMapper;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JdbcExecutor {

    private static final Integer PAGE_NUM = 1;
    private static final Integer PAGE_SIZE = 200;

    private final MybatisMapper mybatisMapper;

    public JdbcExecutor(MybatisMapper mybatisMapper) {
        this.mybatisMapper = mybatisMapper;
    }

    public Object selectOne(DsInfoDTO dsInfoDTO, String sql, Object params) {
        List<Map<String, Object>> maps = selectList(dsInfoDTO, sql, params);
        return maps.size() > 0 ? maps.get(0) : Collections.emptyMap();
    }

    public List selectList(DsInfoDTO dsInfoDTO, String sql, Object params) {
        PageResult page = selectPage(dsInfoDTO, sql, params, PAGE_NUM, PAGE_SIZE);
        return page.getRecords();
    }

    public PageResult selectPage(DsInfoDTO dsInfoDTO, String sql, Object params, int pageNum, int pageSize) {
        Map<String, Object> paramsMap = buildParams(sql, params);
        return (PageResult) doSwitchDataSource(dsInfoDTO, () -> {
            List<Map<String, Object>> list = mybatisMapper.select(paramsMap);
            PageResult pageResult = new PageResult(Long.valueOf(pageNum), Long.valueOf(pageSize), Long.MAX_VALUE);
            pageResult.setRecords(list);
            return pageResult;
        });
    }

    public int insert(DsInfoDTO dsInfoDTO, String sql, Object params) {
        Map<String, Object> paramsMap = buildParams(sql, params);
        return (int) doSwitchDataSource(dsInfoDTO, () -> {
            return mybatisMapper.insert(paramsMap);
        });
    }

    public int update(DsInfoDTO dsInfoDTO, String sql, Object params) {
        Map<String, Object> paramsMap = buildParams(sql, params);
        return (int) doSwitchDataSource(dsInfoDTO, () -> {
            return mybatisMapper.update(paramsMap);
        });
    }

    public int delete(DsInfoDTO dsInfoDTO, String sql, Object params) {
        Map<String, Object> paramsMap = buildParams(sql, params);
        return (int) doSwitchDataSource(dsInfoDTO, () -> {
            return mybatisMapper.delete(paramsMap);
        });
    }

    private Map<String, Object> buildParams(String sql, Object params) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(MybatisConstant.SQL_SCRIPT, sql);
        if (params instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) params;
            paramsMap.putAll(map);
        }
        return paramsMap;
    }

    private Object doSwitchDataSource(DsInfoDTO dsInfoDTO, Supplier supplier) {
        try {
//            DataSourceContext.setDataSource(dataSourceId);
            return supplier.get();
        } finally {
//            DataSourceContext.remove();
        }
    }
}
