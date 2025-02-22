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
package cn.sliew.carp.module.dag.config;

import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.workflow.stage.model.resolver.DefaultStepResolver;
import cn.sliew.carp.module.workflow.stage.model.resolver.DefaultTaskResolver;
import cn.sliew.carp.module.workflow.stage.model.resolver.StepResolver;
import cn.sliew.carp.module.workflow.stage.model.resolver.TaskResolver;
import cn.sliew.carp.module.workflow.stage.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DagConfig {

    @Bean
    public StepResolver carpStepResolver(List<StageDefinitionBuilder> stageDefinitionBuilders) {
        return new DefaultStepResolver(stageDefinitionBuilders);
    }

    @Bean
    public TaskResolver carpTaskResolver(@Autowired List<Task> tasks) {
        return new DefaultTaskResolver(tasks, true);
    }
}
