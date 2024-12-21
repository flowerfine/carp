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

import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.spring.util.PageUtil;
import cn.sliew.carp.module.plugin.service.ExpService;
import cn.sliew.carp.module.plugin.service.PluginService;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginListParam;
import cn.sliew.milky.common.util.JacksonUtil;
import com.mqttsnet.thinglinks.PluginServer;
import com.mqttsnet.thinglinks.open.exp.client.ExpAppContext;
import com.mqttsnet.thinglinks.open.exp.client.ExpAppContextSpiFactory;
import com.mqttsnet.thinglinks.open.exp.client.Plugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpServiceImpl implements ExpService {

    private final ExpAppContext expAppContext = ExpAppContextSpiFactory.getFirst();

    @Autowired
    private PluginService pluginService;
    @Autowired
    private PluginServer pluginServer;

    public void afterPropertiesSet() throws Exception {
        List<String> runtimeAllPluginId = pluginServer.getRuntimeAllPluginId();
        log.info("runtimeAllPluginId: {}", JacksonUtil.toJsonString(runtimeAllPluginId));
        // 读取数据库中启用的插件，然后下载，然后更新
        CarpPluginListParam listParam = new CarpPluginListParam();
        listParam.setStatus(YesOrNo.YES.getValue());
        List<CarpPluginDTO> carpPluginDTOS = pluginService.listAll(listParam);
        for (CarpPluginDTO dto : carpPluginDTOS) {
            try {
                Path path = pluginService.internalDownloadPlugin(dto);
                preload(path.toAbsolutePath().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public PageResult<String> page(PageParam param) {
        return PageUtil.buildPage(param, list());
    }

    @Override
    public List<String> list() {
        return expAppContext.getAllPluginId().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public Plugin preload(String url) {
        Plugin plugin = expAppContext.preLoad(new File(url));
        log.info("插件预加载成功，插件ID: {}", plugin.getPluginId());
        return plugin;
    }

    @Override
    public Plugin install(String url) throws Throwable {
        Plugin plugin = expAppContext.load(new File(url));
        log.info("插件安装成功，插件ID: {}", plugin.getPluginId());
        return plugin;
    }

    @Override
    public void uninstall(String pluginId) throws Throwable {
        expAppContext.unload(pluginId);
        log.info("插件卸载成功，插件ID: {}", pluginId);
    }
}
