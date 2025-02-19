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

import cn.sliew.module.workflow.stage.model.StageDefinition;
import cn.sliew.module.workflow.stage.model.TaskDefinition;
import cn.sliew.module.workflow.stage.model.param.ParamDataType;
import cn.sliew.module.workflow.stage.model.param.StageInputParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class LogStageDefinition implements StageDefinition {

    public static final String STAGE_TYPE = "log";

    @Autowired
    private LogTaskDefinition logTaskDefinition;

    @Override
    public String getCategory() {
        return "demo";
    }

    @Override
    public String getType() {
        return STAGE_TYPE;
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String getProvider() {
        return "internal";
    }

    @Override
    public String getRemark() {
        return "a simple demo stage";
    }

    @Override
    public List<StageInputParam> getInputParams() {
        return Arrays.asList(
                StageInputParam.builder().name("stage-input-param-1").alias("stage-input-param-1").value("stage-input-param-1").type(ParamDataType.STRING).build(),
                StageInputParam.builder().name("stage-input-param-2").alias("stage-input-param-2").value("stage-input-param-2").type(ParamDataType.STRING).build()
        );
    }

    @Override
    public List<TaskDefinition> getTasks() {
        return Collections.singletonList(logTaskDefinition);
    }
}
