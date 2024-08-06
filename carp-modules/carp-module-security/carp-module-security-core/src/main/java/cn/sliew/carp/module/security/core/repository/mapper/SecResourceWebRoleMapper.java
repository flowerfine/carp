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

import cn.sliew.carp.framework.common.dict.security.SecRoleStatus;
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWeb;
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWebRole;
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWebVO;
import cn.sliew.carp.module.security.core.repository.entity.SecRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecResourceWebRoleMapper extends BaseMapper<SecResourceWebRole> {

    /**
     * 查询 资源-web 关联的角色
     */
    Page<SecRole> selectRelatedRolesByWebResource(Page page,
                                                  @Param("resourceWebId") Long resourceWebId,
                                                  @Param("status") SecRoleStatus status,
                                                  @Param("name") String name);

    /**
     * 查询 资源-web 未关联的角色
     */
    Page<SecRole> selectUnrelatedRolesByWebResource(Page page,
                                                    @Param("resourceWebId") Long resourceWebId,
                                                    @Param("status") SecRoleStatus status,
                                                    @Param("name") String name);

    /**
     * 查询所有 资源-web，包含角色关联信息
     */
    List<SecResourceWebVO> selectAllResourceWebWithAuthorizeStatus(@Param("roleId") Long roleId, @Param("pid") Long pid);

    /**
     * 查询角色关联的 资源-web
     */
    List<SecResourceWeb> selectRelatedWebResourceByRole(@Param("roleId") Long roleId);
}
