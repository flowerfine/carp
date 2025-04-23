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

import cn.sliew.carp.module.persistence.service.sql.entity.ResourceStorage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceStorageMapper {

    @Select("SELECT * FROM ${table} WHERE namespace = #{namespace} ORDER BY ID DESC")
    Page<ResourceStorage> page(Page<ResourceStorage> page, @Param("table") String table, @Param("namespace") String namespace);

    @Select("SELECT * FROM ${table} WHERE namespace = #{namespace} ORDER BY ID DESC")
    List<ResourceStorage> list(@Param("table") String table, @Param("namespace") String namespace);

    @Select("SELECT * FROM ${table} WHERE id = #{id}")
    ResourceStorage get(@Param("table") String table, @Param("Long") Long id);

    @Insert("INSERT INTO ${table} (namespace, metadata, spec, status, remark, version, creator, create_time, editor, update_time) VALUES " +
            "(#{entity.namespace}, #{entity.metadata}, #{entity.spec}, #{entity.status}, #{entity.remark}, 0, #{entity.creator}, #{entity.createTime}, #{entity.editor}, #{entity.updateTime})")
    int insert(@Param("table") String table, @Param("entity") ResourceStorage entity);

    @Insert("UPDATE ${table} SET namespace = #{entity.namespace}, metadata = #{entity.metadata}, spec = #{entity.spec}, status = #{entity.status}, version = version + 1, remark = #{entity.remark}, editor = #{entity.editor}, update_time = #{entity.updateTime} " +
            "WHERE id = #{entity.id}")
    int update(@Param("table") String table, @Param("entity") ResourceStorage entity);

    @Select("DELETE FROM ${table} WHERE id = #{id}")
    int delete(@Param("table") String table, @Param("Long") Long id);
}
