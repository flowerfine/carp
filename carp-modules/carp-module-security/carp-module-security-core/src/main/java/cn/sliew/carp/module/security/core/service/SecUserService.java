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
package cn.sliew.carp.module.security.core.service;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.security.core.repository.entity.SecUser;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.SecUserAddParam;
import cn.sliew.carp.module.security.core.service.param.SecUserListParam;
import cn.sliew.carp.module.security.core.service.param.SecUserUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SecUserService extends IService<SecUser> {

    PageResult<SecUserDTO> page(SecUserListParam param);

    List<SecUserDTO> listAll(SecUserListParam param);

    SecUserDTO get(Long id);

    Optional<SecUserDTO> getByUserName(String userName);

    boolean add(SecUserAddParam param);

    boolean update(SecUserUpdateParam param);

    boolean delete(Long id);

    boolean deleteBatch(Collection<Long> ids);
}
