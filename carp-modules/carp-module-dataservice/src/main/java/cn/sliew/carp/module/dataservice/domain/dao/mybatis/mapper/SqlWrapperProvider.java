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
package cn.sliew.carp.module.dataservice.domain.dao.mybatis.mapper;

import cn.sliew.carp.module.dataservice.domain.dao.mybatis.MybatisConstant;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public class SqlWrapperProvider {

    private static final String[] tags = {"</if>", "</foreach>", "</where>", "</set>", "</choose>", "</when>", "</trim>", "</otherwise"};

    public String wrapSelect(Map<String, Object> params) {
        String sql = params.get(MybatisConstant.SQL_SCRIPT).toString();
        params.remove(MybatisConstant.SQL_SCRIPT);
        return addScript(sql);
    }

    public String wrapInsert(Map<String, Object> params) {
        String sql = params.get(MybatisConstant.SQL_SCRIPT).toString();
        params.remove(MybatisConstant.SQL_SCRIPT);
        return addScript(sql);
    }

    public String wrapUpdate(Map<String, Object> params) {
        String sql = params.get(MybatisConstant.SQL_SCRIPT).toString();
        params.remove(MybatisConstant.SQL_SCRIPT);
        return addScript(sql);
    }

    public String wrapDelete(Map<String, Object> params) {
        String sql = params.get(MybatisConstant.SQL_SCRIPT).toString();
        params.remove(MybatisConstant.SQL_SCRIPT);
        return addScript(sql);
    }

    private String addScript(String sql) {
        for (String tag : tags) {
            if (sql.contains(tag)) {
                sql = "<script> " + sql + " </script>";
                break;
            }
        }
        return sql;
    }
}
