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

import org.pf4j.PluginRepository;

import java.io.FileFilter;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * todo 从数据库配置读取插件加载地址
 */
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
        return Collections.emptyList();
    }

    @Override
    public boolean deletePluginPath(Path path) {
        return true;
    }
}
