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
package cn.sliew.carp.module.persistence.service.sql.mapper;

import cn.sliew.carp.module.persistence.service.sql.entity.ResourceStorageHistory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceStorageHistoryMapper {

    @Select("SELECT * FROM ${table} WHERE namespace = #{namespace}, resource_id = #{resourceId} ORDER BY ID DESC")
    Page<ResourceStorageHistory> page(Page<ResourceStorageHistory> page, @Param("table") String table, @Param("namespace") String namespace, @Param("resourceId") Long resourceId);

    @Select("SELECT * FROM ${table} WHERE namespace = #{namespace}, resource_id = #{resourceId} ORDER BY ID DESC")
    List<ResourceStorageHistory> list(@Param("table") String table, @Param("namespace") String namespace, @Param("resourceId") Long resourceId);

    @Select("SELECT * FROM ${table} WHERE id = #{id}")
    ResourceStorageHistory get(@Param("table") String table, @Param("Long") Long id);

    @Insert("INSERT INTO ${table} (resource_id, namespace, metadata, spec, status, remark, version, creator, create_time, editor, update_time) VALUES " +
            "(#{entity.resourceId}, #{entity.namespace}, #{entity.metadata}, #{entity.spec}, #{entity.status}, #{entity.remark}, #{entity.version}, #{entity.creator}, #{entity.createTime}, #{entity.editor}, #{entity.updateTime})")
    int insert(@Param("table") String table, @Param("entity") ResourceStorageHistory entity);

    @Select("DELETE FROM ${table} WHERE id = #{id}")
    int delete(@Param("table") String table, @Param("Long") Long id);

    @Select("DELETE FROM ${table} WHERE namespace = #{namespace}, resource_id = #{resourceId}")
    int deleteHistory(@Param("table") String table, @Param("namespace") String namespace, @Param("resourceId") Long resourceId);
}
