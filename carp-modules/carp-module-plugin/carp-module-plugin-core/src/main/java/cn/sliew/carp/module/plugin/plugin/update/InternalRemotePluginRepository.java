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
package cn.sliew.carp.module.plugin.plugin.update;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.plugin.repository.entity.CarpPluginInfo;
import cn.sliew.carp.module.plugin.repository.mapper.CarpPluginInfoMapper;
import cn.sliew.carp.module.plugin.service.convert.CarpPluginRepositoryInfoConvert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * todo 切换至 CarpPluginInfoService 实现
 */
@Component
public class InternalRemotePluginRepository extends ServiceImpl<CarpPluginInfoMapper, CarpPluginInfo> implements RemotePluginRepository, InitializingBean {

    private PluginRepositoryInfo repositoryInfo;

    @Override
    public void afterPropertiesSet() throws Exception {
        repositoryInfo = new PluginRepositoryInfo();
        repositoryInfo.setId("id");
        repositoryInfo.setUrl("https://repo1.maven.org/maven2/cn/sliew/");
        repositoryInfo.setProvider("carp");
    }

    @Override
    public PluginRepositoryInfo getInfo() {
        return repositoryInfo;
    }

    @Override
    public PageResult<RemotePluginInfo> page(PageParam param) {
        Page<CarpPluginInfo> page = new Page<>(param.getCurrent(), param.getPageSize());
        Page<CarpPluginInfo> carpPluginInfoPage = page(page, Wrappers.emptyWrapper());
        PageResult<RemotePluginInfo> pageResult = new PageResult<>(carpPluginInfoPage.getCurrent(), carpPluginInfoPage.getSize(), carpPluginInfoPage.getTotal());
        List<RemotePluginInfo> remotePluginInfos = carpPluginInfoPage.getRecords().stream().map(record -> {
            RemotePluginInfo dto = CarpPluginRepositoryInfoConvert.INSTANCE.toDto(record);
            dto.setProvider(repositoryInfo.getUrl());
            return dto;
        }).collect(Collectors.toList());
        pageResult.setRecords(remotePluginInfos);
        return pageResult;
    }

    @Override
    public List<RemotePluginInfo> getAll() {
        List<CarpPluginInfo> entities = list(Wrappers.emptyWrapper());
        return entities.stream().map(record -> {
            RemotePluginInfo dto = CarpPluginRepositoryInfoConvert.INSTANCE.toDto(record);
            dto.setProvider(repositoryInfo.getUrl());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public RemotePluginInfo get(String id) {
        CarpPluginInfo entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("plugin info not exists for id: " + id));
        RemotePluginInfo dto = CarpPluginRepositoryInfoConvert.INSTANCE.toDto(entity);
        dto.setProvider(repositoryInfo.getUrl());
        return dto;
    }
}
