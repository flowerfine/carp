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
package cn.sliew.carp.module.orca.spinnaker.api.model.jackson;

import cn.sliew.carp.module.orca.spinnaker.api.model.jackson.mixin.PipelineExecutionMixin;
import cn.sliew.carp.module.orca.spinnaker.api.model.jackson.mixin.StageExecutionMixin;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class OrcaModule extends SimpleModule {

    public OrcaModule() {
        super("apiTypes", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(TaskExecution.class, TaskExecutionImpl.class);
        resolver.addMapping(StageExecution.class, StageExecutionImpl.class);
        resolver.addMapping(PipelineExecution.class, PipelineExecutionImpl.class);
        setMixInAnnotation(StageExecution.class, StageExecutionMixin.class);
        setMixInAnnotation(PipelineExecution.class, PipelineExecutionMixin.class);
        setAbstractTypes(resolver);
    }
}
