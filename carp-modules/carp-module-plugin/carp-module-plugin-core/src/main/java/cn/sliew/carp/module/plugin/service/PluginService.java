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

import cn.sliew.carp.framework.common.dict.common.CarpYesOrNo;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.plugin.repository.entity.CarpPlugin;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginAddParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginListParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginPageParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginUpdateParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PluginService extends IService<CarpPlugin> {

    PageResult<CarpPluginDTO> list(CarpPluginPageParam param);

    List<CarpPluginDTO> listAll(CarpPluginListParam param);

    CarpPluginDTO get(Long id);

    Optional<CarpPluginDTO> getByPluginId(String pluginId);

    boolean add(CarpPluginAddParam param);

    boolean update(CarpPluginUpdateParam param);

    boolean updateStatus(String pluginId, CarpYesOrNo status);

    boolean delete(Long id);

    boolean deleteBatch(Collection<Long> ids);

    Path internalDownloadPlugin(CarpPluginDTO dto) throws IOException;

    boolean enable(Long id);

    boolean disable(Long id);
}
