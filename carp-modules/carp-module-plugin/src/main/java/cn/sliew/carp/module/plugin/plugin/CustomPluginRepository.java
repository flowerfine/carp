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

import cn.sliew.carp.framework.common.nio.FileUtil;
import cn.sliew.carp.module.plugin.service.CarpPluginReleaseService;
import cn.sliew.carp.module.plugin.service.CarpPluginStatusService;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginReleaseDTO;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginRepository;
import org.pf4j.PluginState;
import org.springframework.util.CollectionUtils;

import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * todo 读取内置的插件仓库
 */
@Slf4j
public class CustomPluginRepository implements PluginRepository {

    private CarpPluginReleaseService carpPluginReleaseService;
    private CarpPluginStatusService carpPluginStatusService;

    protected FileFilter filter;

    public CustomPluginRepository(CarpPluginReleaseService carpPluginReleaseService, CarpPluginStatusService carpPluginStatusService) {
        this(carpPluginReleaseService, carpPluginStatusService, null);
    }

    public CustomPluginRepository(CarpPluginReleaseService carpPluginReleaseService, CarpPluginStatusService carpPluginStatusService, FileFilter filter) {
        this.carpPluginReleaseService = carpPluginReleaseService;
        this.carpPluginStatusService = carpPluginStatusService;
        this.filter = filter;
    }

    @Override
    public List<Path> getPluginPaths() {
        List<CarpPluginStatusDTO> startedPlugins = carpPluginStatusService.list(PluginState.STARTED);
        if (CollectionUtils.isEmpty(startedPlugins)) {
            return Collections.emptyList();
        }
        // 读取数据库中启用的插件，然后下载，然后更新
        List<Path> paths = new ArrayList<>();
        for (CarpPluginStatusDTO statusDTO : startedPlugins) {
            CarpPluginReleaseDTO releaseDTO = carpPluginReleaseService.getByUuid(statusDTO.getPluginUuid());
            try {
                Path path = carpPluginReleaseService.internalDownloadPlugin(releaseDTO);
                if (Objects.isNull(filter) || filter.accept(path.toFile())) {
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
