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

import cn.sliew.carp.module.security.core.repository.entity.SecRole;
import cn.sliew.carp.module.security.core.repository.entity.SecUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecUserMapper extends BaseMapper<SecUser> {

    /**
     * 批量修改用户状态
     *
     * @param idList     用户id列表
     * @param userStatus 用户状态
     * @return int
     */
    @Update({"<script>" +
        "update sec_user " +
        "set `status` = #{userStatus} " +
        "where id in " +
        "<foreach item=\"id\" index=\"index\" collection =\"idList\" open=\"(\" close=\")\" separator=\",\"> #{id}</foreach>" +
        "</script>"})
    int batchUpdateUserStatus(@Param("idList") List<Integer> idList,
                              @Param("userStatus") String userStatus);

    /**
     * 分页查询
     *
     * @param page   分页参数
     * @param deptId 部门编码
     * @param roleId 角色编码
     * @param secUser   用户参数
     * @return list
     */
    Page<SecUser> selectPage(IPage<?> page, @Param("deptId") String deptId,
                             @Param("roleId") String roleId, @Param("user") SecUser secUser);

    /**
     * 根据角色或者部门id查询
     *
     * @param deptId    部门id
     * @param roleId    角色id
     * @param userName  userName
     * @param direction 1:target 0:source
     * @return list
     */
    List<SecUser> selectByRoleOrDept(@Param("deptId") String deptId, @Param("roleId") String roleId,
                                     @Param("userName") String userName,
                                     @Param("direction") String direction);

    /**
     * 查询用户对应所有角色权限信息
     *
     * @param userName user name
     * @return role list
     */
    List<SecRole> selectAllPrivilege(@Param("userName") String userName);
}
