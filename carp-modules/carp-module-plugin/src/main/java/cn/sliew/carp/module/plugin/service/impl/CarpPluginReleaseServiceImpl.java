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

import cn.hutool.http.HttpUtil;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.nio.FileUtil;
import cn.sliew.carp.framework.spring.util.SystemUtil;
import cn.sliew.carp.module.plugin.repository.entity.CarpPluginRelease;
import cn.sliew.carp.module.plugin.repository.mapper.CarpPluginReleaseMapper;
import cn.sliew.carp.module.plugin.service.CarpPluginReleaseService;
import cn.sliew.carp.module.plugin.service.convert.CarpPluginReleaseConvert;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginReleaseDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginReleasePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class CarpPluginReleaseServiceImpl extends ServiceImpl<CarpPluginReleaseMapper, CarpPluginRelease> implements CarpPluginReleaseService {

    @Override
    public PageResult<CarpPluginReleaseDTO> page(CarpPluginReleasePageParam param) {
        Page<CarpPluginRelease> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<CarpPluginRelease> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginRelease.class)
                .eq(CarpPluginRelease::getPluginId, param.getPluginId())
                .orderByAsc(CarpPluginRelease::getVersion);
        Page<CarpPluginRelease> carpPluginReleasePage = page(page, queryChainWrapper);
        PageResult<CarpPluginReleaseDTO> pageResult = new PageResult<>(carpPluginReleasePage.getCurrent(), carpPluginReleasePage.getSize(), carpPluginReleasePage.getTotal());
        pageResult.setRecords(CarpPluginReleaseConvert.INSTANCE.toDto(carpPluginReleasePage.getRecords()));
        return pageResult;
    }

    @Override
    public List<CarpPluginReleaseDTO> list(CarpPluginReleasePageParam param) {
        LambdaQueryWrapper<CarpPluginRelease> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginRelease.class)
                .eq(CarpPluginRelease::getPluginId, param.getPluginId())
                .orderByAsc(CarpPluginRelease::getVersion);
        List<CarpPluginRelease> entities = list(queryChainWrapper);
        return CarpPluginReleaseConvert.INSTANCE.toDto(entities);
    }

    @Override
    public CarpPluginReleaseDTO get(Long id) {
        CarpPluginRelease entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("plugin release not exists for id: " + id));
        return CarpPluginReleaseConvert.INSTANCE.toDto(entity);
    }

    @Override
    public CarpPluginReleaseDTO getByUuid(String pluginUuid) {
        LambdaQueryWrapper<CarpPluginRelease> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginRelease.class)
                .eq(CarpPluginRelease::getUuid, pluginUuid);
        CarpPluginRelease entity = getOne(queryChainWrapper);
        return CarpPluginReleaseConvert.INSTANCE.toDto(entity);
    }

    @Override
    public Path internalDownloadPlugin(CarpPluginReleaseDTO dto) throws IOException {
        Path path = FileUtil.createFile(SystemUtil.getPluginsPath(), FilenameUtils.getName(dto.getUrl()));
        HttpUtil.downloadFileFromUrl(dto.getUrl(), path.toAbsolutePath().toFile().getAbsolutePath());
        return path;
    }
}
