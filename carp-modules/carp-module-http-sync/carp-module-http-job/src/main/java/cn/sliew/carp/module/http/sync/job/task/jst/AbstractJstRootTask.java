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
package cn.sliew.carp.module.http.sync.job.task.jst;

import cn.sliew.carp.module.http.sync.framework.model.processor.AbstractRootTask;
import cn.sliew.carp.module.http.sync.job.remote.JstRemoteService;
import cn.sliew.carp.module.http.sync.job.repository.entity.jst.JstAuth;
import lombok.Getter;

@Getter
public abstract class AbstractJstRootTask<Sub extends AbstractJstSubTask> extends AbstractRootTask<Sub> {

    private JstRemoteService jstRemoteService;
    private JstAuth jstAuth;

    public AbstractJstRootTask(Long rootTaskId, JstRemoteService jstRemoteService, JstAuth jstAuth) {
        super(rootTaskId);
        this.jstRemoteService = jstRemoteService;
        this.jstAuth = jstAuth;
    }
}
