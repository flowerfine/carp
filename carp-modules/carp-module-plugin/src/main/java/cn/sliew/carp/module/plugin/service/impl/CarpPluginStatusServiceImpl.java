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

package cn.sliew.carp.module.plugin.service.impl;

import cn.sliew.carp.module.plugin.repository.entity.CarpPluginStatus;
import cn.sliew.carp.module.plugin.repository.mapper.CarpPluginStatusMapper;
import cn.sliew.carp.module.plugin.service.CarpPluginStatusService;
import cn.sliew.carp.module.plugin.service.convert.CarpPluginStatusConvert;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginStatusDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pf4j.PluginState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarpPluginStatusServiceImpl extends ServiceImpl<CarpPluginStatusMapper, CarpPluginStatus> implements CarpPluginStatusService {

    @Override
    public List<CarpPluginStatusDTO> list(PluginState state) {
        LambdaQueryWrapper<CarpPluginStatus> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginStatus.class)
                .eq(Objects.nonNull(state), CarpPluginStatus::getStatus, state);
        List<CarpPluginStatus> entities = list(queryChainWrapper);
        return CarpPluginStatusConvert.INSTANCE.toDto(entities);
    }

    @Override
    public Optional<CarpPluginStatusDTO> get(String pluginUuid) {
        LambdaQueryWrapper<CarpPluginStatus> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginStatus.class)
                .eq(CarpPluginStatus::getPluginUuid, pluginUuid);
        Optional<CarpPluginStatus> optional = getOneOpt(queryChainWrapper);
        return optional.map(record -> CarpPluginStatusConvert.INSTANCE.toDto(record));
    }

    @Override
    public boolean upsert(String pluginUuid, PluginState state) {
        CarpPluginStatus record = new CarpPluginStatus();
        record.setPluginUuid(pluginUuid);
        record.setStatus(state);

        LambdaQueryWrapper<CarpPluginStatus> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginStatus.class)
                .eq(CarpPluginStatus::getPluginUuid, pluginUuid);
        if (getOneOpt(queryChainWrapper).isPresent()) {
            return update(record, queryChainWrapper);
        } else {
            return save(record);
        }
    }

    @Override
    public boolean delete(String pluginUuid) {
        LambdaQueryWrapper<CarpPluginStatus> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginStatus.class)
                .eq(CarpPluginStatus::getPluginUuid, pluginUuid);
        return remove(queryChainWrapper);
    }
}
