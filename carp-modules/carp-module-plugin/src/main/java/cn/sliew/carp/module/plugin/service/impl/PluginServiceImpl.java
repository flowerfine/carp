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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.dict.plugin.PluginType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.nio.FileUtil;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.spring.util.SystemUtil;
import cn.sliew.carp.module.plugin.repository.entity.CarpPlugin;
import cn.sliew.carp.module.plugin.repository.mapper.CarpPluginMapper;
import cn.sliew.carp.module.plugin.service.Pf4jService;
import cn.sliew.carp.module.plugin.service.PluginService;
import cn.sliew.carp.module.plugin.service.convert.CarpPluginConvert;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginAddParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginListParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PluginServiceImpl extends ServiceImpl<CarpPluginMapper, CarpPlugin> implements PluginService {

    @Autowired
    private Pf4jService pf4jService;

    @Override
    public PageResult<CarpPluginDTO> list(CarpPluginListParam param) {
        Page<CarpPlugin> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<CarpPlugin> queryChainWrapper = Wrappers.lambdaQuery(CarpPlugin.class)
                .like(StringUtils.hasText(param.getName()), CarpPlugin::getName, param.getName())
                .eq(param.getType() != null, CarpPlugin::getType, param.getType())
                .eq(param.getStatus() != null, CarpPlugin::getStatus, param.getStatus())
                .orderByAsc(CarpPlugin::getId);
        Page<CarpPlugin> secRolePage = page(page, queryChainWrapper);
        PageResult<CarpPluginDTO> pageResult = new PageResult<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        pageResult.setRecords(CarpPluginConvert.INSTANCE.toDto(secRolePage.getRecords()));
        return pageResult;
    }

    @Override
    public List<CarpPluginDTO> listAll() {
        LambdaQueryWrapper<CarpPlugin> queryChainWrapper = Wrappers.lambdaQuery(CarpPlugin.class)
                .orderByAsc(CarpPlugin::getId);
        List<CarpPlugin> entities = list(queryChainWrapper);
        return CarpPluginConvert.INSTANCE.toDto(entities);
    }

    @Override
    public CarpPluginDTO get(Long id) {
        CarpPlugin entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("plugin not exists for id: " + id));
        return CarpPluginConvert.INSTANCE.toDto(entity);
    }

    @Override
    public Optional<CarpPluginDTO> getByPluginId(String pluginId) {
        LambdaQueryWrapper<CarpPlugin> queryChainWrapper = Wrappers.lambdaQuery(CarpPlugin.class)
                .eq(CarpPlugin::getPluginId, pluginId);
        return getOneOpt(queryChainWrapper).map(CarpPluginConvert.INSTANCE::toDto);
    }

    @Override
    public boolean add(CarpPluginAddParam param) {
        CarpPlugin entity = BeanUtil.copyProperties(param, CarpPlugin.class);
        entity.setType(PluginType.INTERNAL);
        entity.setStatus(YesOrNo.NO);
        return save(entity);
    }

    @Override
    public boolean update(CarpPluginUpdateParam param) {
        CarpPlugin entity = BeanUtil.copyProperties(param, CarpPlugin.class);
        return updateById(entity);
    }

    @Override
    public boolean updateStatus(String pluginId, YesOrNo status) {
        LambdaUpdateWrapper<CarpPlugin> updateWrapper = Wrappers.lambdaUpdate(CarpPlugin.class)
                .eq(CarpPlugin::getPluginId, pluginId)
                .set(CarpPlugin::getStatus, status);
        return update(updateWrapper);
    }

    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        return removeByIds(ids);
    }

    @Override
    public boolean enable(Long id) {
        // 下载插件至本地
        CarpPluginDTO dto = get(id);
        Path path = null;
        try {
            path = FileUtil.createFile(SystemUtil.getPluginsPath(), FilenameUtils.getName(dto.getUrl()));
            HttpUtil.downloadFileFromUrl(dto.getUrl(), path.toAbsolutePath().toFile().getAbsolutePath());
            // 启用插件
            String pluginId = pf4jService.loadPlugin(path);
            // 更新插件信息
            LambdaUpdateWrapper<CarpPlugin> updateWrapper = Wrappers.lambdaUpdate(CarpPlugin.class)
                    .eq(CarpPlugin::getId, id)
                    .set(CarpPlugin::getPluginId, pluginId);
            update(updateWrapper);
            // 启用插件信息
            return pf4jService.enablePlugin(pluginId);
            // 插件启用完毕后，不能删除通过 http 下载的 jar。插件后续仍需读取 LegacyExtensionFinder.EXTENSIONS_RESOURCE
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            if (path != null) {
                try {
                    FileUtil.deleteFile(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean disable(Long id) {
        CarpPluginDTO dto = get(id);

        // 禁用插件
        pf4jService.disablePlugin(dto.getPluginId());
        // 更新插件信息
        LambdaUpdateWrapper<CarpPlugin> updateWrapper = Wrappers.lambdaUpdate(CarpPlugin.class)
                .eq(CarpPlugin::getId, id)
                .set(CarpPlugin::getPluginId, null);
        return update(updateWrapper);
    }
}
