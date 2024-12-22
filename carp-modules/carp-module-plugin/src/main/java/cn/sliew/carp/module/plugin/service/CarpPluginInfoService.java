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

package cn.sliew.carp.module.plugin.service;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.plugin.repository.entity.CarpPluginInfo;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginInfoDTO;
import cn.sliew.carp.module.plugin.service.param.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

public interface CarpPluginInfoService extends IService<CarpPluginInfo> {

    PageResult<CarpPluginInfoDTO> page(CarpPluginInfoPageParam param);

    List<CarpPluginInfoDTO> list(CarpPluginInfoPageParam param);

    CarpPluginInfoDTO get(Long id);

    boolean add(CarpPluginInfoAddParam param);

    boolean update(CarpPluginInfoUpdateParam param);

    boolean delete(Long id);

    boolean deleteBatch(Collection<Long> ids);
}