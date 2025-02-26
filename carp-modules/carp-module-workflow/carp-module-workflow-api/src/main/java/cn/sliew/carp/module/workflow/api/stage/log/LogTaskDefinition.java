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
package cn.sliew.carp.module.workflow.api.stage.log;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.workflow.stage.model.TaskDefinition;
import cn.sliew.carp.module.workflow.stage.model.domain.param.ParamDataType;
import cn.sliew.carp.module.workflow.stage.model.domain.param.TaskInputParam;
import cn.sliew.carp.module.workflow.stage.model.domain.param.TaskOutputParam;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LogTaskDefinition implements TaskDefinition {

    public static final String TASK_TYPE = "log";

    @Override
    public String getType() {
        return TASK_TYPE;
    }

    @Override
    public String getName() {
        return UUIDUtil.randomUUId();
    }

    @Override
    public List<TaskInputParam> getInputParams() {
        return Arrays.asList(
                TaskInputParam.builder().name("task-input-param-1").alias("task-input-param-1").value("task-input-param-1").type(ParamDataType.STRING).build(),
                TaskInputParam.builder().name("task-input-param-2").alias("task-input-param-2").value("task-input-param-2").type(ParamDataType.STRING).build()
        );
    }

    @Override
    public List<TaskOutputParam> getOutputParams() {
        return Arrays.asList(
                TaskOutputParam.builder().name("task-output-param-1").alias("task-output-param-1").type(ParamDataType.STRING).build(),
                TaskOutputParam.builder().name("task-output-param-2").alias("task-output-param-2").type(ParamDataType.STRING).build()
        );
    }
}
