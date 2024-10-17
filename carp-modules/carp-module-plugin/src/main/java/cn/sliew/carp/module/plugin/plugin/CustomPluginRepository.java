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

package cn.sliew.carp.module.plugin.plugin;

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.nio.FileUtil;
import cn.sliew.carp.module.plugin.service.PluginService;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginListParam;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginRepository;

import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * todo 从数据库配置读取插件加载地址
 */
@Slf4j
public class CustomPluginRepository implements PluginRepository {

    protected FileFilter filter;

    public CustomPluginRepository() {
        this(null);
    }

    public CustomPluginRepository(FileFilter filter) {
        this.filter = filter;
    }

    @Override
    public List<Path> getPluginPaths() {
        // 读取数据库中启用的插件，然后下载，然后更新
        PluginService pluginService = SpringUtil.getBean(PluginService.class);
        CarpPluginListParam listParam = new CarpPluginListParam();
        listParam.setStatus(YesOrNo.YES.getValue());
        List<CarpPluginDTO> carpPluginDTOS = pluginService.listAll(listParam);
        List<Path> paths = new ArrayList<>();
        for (CarpPluginDTO dto : carpPluginDTOS) {
            try {
                Path path = pluginService.internalDownloadPlugin(dto);
                if (filter == null || filter.accept(path.toFile())) {
                    paths.add(path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return paths;
    }

    @Override
    public boolean deletePluginPath(Path path) {
        try {
            FileUtil.deleteFile(path);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
