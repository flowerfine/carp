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
package cn.sliew.module.workflow.stage.internal.log;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.module.workflow.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.module.workflow.stage.model.graph.TaskNode;
import org.springframework.stereotype.Component;

@Component
public class LogStep implements StageDefinitionBuilder {

    public static final String STEP_TYPE = "log";

    @Override
    public String getType() {
        return STEP_TYPE;
    }

    @Override
    public void taskGraph(DagStepDTO step, TaskNode.Builder builder) {
        builder.withTask("log-1", LogTask.class);
        builder.withTask("log-2", LogTask.class);
        builder.withTask("log-3", LogTask.class);
    }
}
