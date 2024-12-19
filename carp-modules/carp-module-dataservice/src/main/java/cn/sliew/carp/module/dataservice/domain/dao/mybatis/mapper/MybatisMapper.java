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

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface MybatisMapper {

    @SelectProvider(type = SqlWrapperProvider.class, method = "wrapSelect")
    List<Map<String, Object>> select(Map<String, Object> params);

    @InsertProvider(type = SqlWrapperProvider.class, method = "wrapInsert")
    Integer insert(Map<String, Object> params);

    @UpdateProvider(type = SqlWrapperProvider.class, method = "wrapUpdate")
    Integer update(Map<String, Object> params);

    @UpdateProvider(type = SqlWrapperProvider.class, method = "wrapDelete")
    Integer delete(Map<String, Object> params);
}
