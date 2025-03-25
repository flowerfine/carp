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
package cn.sliew.carp.module.workflow.simple.tasks;

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.module.workflow.simple.tasks.domain.WorkflowTaskRunRet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class BaseLocalStepTask extends BaseLocalTask {

    public Long workflowInstanceId;
    public Long workflowStepInstanceId;
    public Long workflowStepDefinitionId;

    // 如果是 sub-workflow，则为父 workflow 中的 step instance id
    public Long parentWorkflowStepInstanceId;

    // 全局参数
    public ObjectNode globalParams;
    // 清空全局参数
    public Boolean isDeleteParams = false;

    // 输入的执行参数，根据inputParams转换过来的
    public JsonNode params;

    // 全局变量
    public JsonNode globalVariable;
    // 最新获取全局变量的时间
    public Long lastGlobalVariableTimestamp;

    // 上游节点执行的结果，包括result、output
    public JsonNode globalResult;

    private Long retryTimes = 0L;

    public abstract WorkflowTaskRunRet run() throws Exception;

    public void freshGlobalVariable() {
        if (System.currentTimeMillis() / 1000 - lastGlobalVariableTimestamp < 1) {
            return;
        }

        DagInstanceService dagInstanceService = SpringUtil.getBean(DagInstanceService.class);
        DagInstanceDTO dagInstanceDTO = dagInstanceService.get(workflowInstanceId);
        globalVariable = dagInstanceDTO.getInputs();
        lastGlobalVariableTimestamp = System.currentTimeMillis() / 1000;
    }

    public void stop() {

    }
}
