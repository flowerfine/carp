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

package cn.sliew.carp.module.plugin.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.module.plugin.plugin.update.PluginRelease;
import cn.sliew.carp.module.plugin.plugin.update.RemotePluginInfo;
import cn.sliew.carp.module.plugin.repository.entity.CarpPluginInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CarpPluginRepositoryInfoConvert extends BaseConvert<CarpPluginInfo, RemotePluginInfo> {
    CarpPluginRepositoryInfoConvert INSTANCE = Mappers.getMapper(CarpPluginRepositoryInfoConvert.class);

    @Override
    default RemotePluginInfo toDto(CarpPluginInfo carpPluginInfo) {
        RemotePluginInfo remotePluginInfo = new RemotePluginInfo();
        remotePluginInfo.setId(carpPluginInfo.getName());
        remotePluginInfo.setName(carpPluginInfo.getName());
        remotePluginInfo.setProvider(carpPluginInfo.getProvider());
        remotePluginInfo.setRemark(carpPluginInfo.getRemark());
        if (CollectionUtils.isNotEmpty(carpPluginInfo.getReleases())) {
            List<PluginRelease> releases = carpPluginInfo.getReleases().stream().map(carpPluginRelease -> {
                PluginRelease release = new PluginRelease();
                release.setVersion(carpPluginRelease.getVersion());
                release.setUrl(carpPluginRelease.getUrl());
                release.setDate(carpPluginRelease.getCreateTime());
                return release;
            }).collect(Collectors.toList());
            remotePluginInfo.setReleases(releases);
        }
        return remotePluginInfo;
    }
}
