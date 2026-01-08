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
package cn.sliew.carp.module.cep.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.sliew.carp.module.cep.service.CarpCepWorkflowRuntimeService;
import cn.sliew.carp.module.cep.service.dto.runtime.*;
import cn.sliew.carp.module.cep.service.param.runtime.*;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter.IReport;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation.IValidation;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.DefaultWorkflowSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.core.application.WorkflowApplication;
import cn.sliew.milky.common.util.JacksonUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CarpCepWorkflowRuntimeServiceImpl implements CarpCepWorkflowRuntimeService {

    private WorkflowApplication application = new WorkflowApplication();

    @Override
    public ServerInfo getInfo() {
        return new ServerInfo()
                .setName("carp")
                .setRuntime("carp")
                .setVersion("0.0.1-SNAPSHOT")
                .setTime(DateUtil.formatDateTime(new Date()));
    }

    @Override
    public TaskResultDTO getResult(TaskResultParam param) {
        return null;
    }

    @Override
    public IReport getReport(TaskReportParam param) {
        return application.report(param.getTaskID());
    }

    @Override
    public IValidation.ValidationResult validate(TaskValidateParam param) {
        WorkflowInputs inputs = new WorkflowInputs();
        inputs.putAll(param.getInputs());
        InvokeParams params = new InvokeParams();
        params.setInputs(inputs);
        params.setSchema(JacksonUtil.parseJsonString(param.getSchema(), DefaultWorkflowSchema.class));
        return application.validate(params);
    }

    @Override
    public TaskRunDTO run(TaskRunParam param) {
        WorkflowInputs inputs = new WorkflowInputs();
        inputs.putAll(param.getInputs());
        InvokeParams params = new InvokeParams();
        params.setInputs(inputs);
        params.setSchema(JacksonUtil.parseJsonString(param.getSchema(), DefaultWorkflowSchema.class));
        String taskId = application.invoke(params);
        return new TaskRunDTO().setTaskID(taskId);
    }

    @Override
    public TaskCancelDTO cancel(TaskCancelParam param) {
        boolean cancel = application.cancel(param.getTaskID());
        return new TaskCancelDTO().setSuccess(cancel);
    }
}
