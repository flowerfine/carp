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

package cn.sliew.carp.module.security.core.repository.mapper;

import cn.sliew.carp.framework.common.dict.security.RoleStatus;
import cn.sliew.carp.framework.common.dict.security.UserStatus;
import cn.sliew.carp.module.security.core.repository.entity.SecRole;
import cn.sliew.carp.module.security.core.repository.entity.SecUser;
import cn.sliew.carp.module.security.core.repository.entity.SecUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecUserRoleMapper extends BaseMapper<SecUserRole> {

    /**
     * 查询角色关联的用户
     */
    Page<SecUser> selectRelatedUsersByRole(Page page,
                                           @Param("roleId") Long roleId,
                                           @Param("status") UserStatus status,
                                           @Param("userName") String userName);

    /**
     * 查询角色未关联的用户
     */
    Page<SecUser> selectUnrelatedUsersByRole(Page page,
                                             @Param("roleId") Long roleId,
                                             @Param("status") UserStatus status,
                                             @Param("userName") String userName);

    /**
     * 查询用户关联的角色
     */
    List<SecRole> selectRelatedRolesByUser(@Param("userId") Long userId,
                                           @Param("status") RoleStatus status,
                                           @Param("name") String name);

    /**
     * 查询用户未关联的角色
     */
    List<SecRole> selectUnrelatedRolesByUser(@Param("userId") Long userId,
                                             @Param("status") RoleStatus status,
                                             @Param("name") String name);
}
