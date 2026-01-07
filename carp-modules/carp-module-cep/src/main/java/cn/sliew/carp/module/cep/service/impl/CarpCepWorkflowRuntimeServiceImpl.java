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
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CarpCepWorkflowRuntimeServiceImpl implements CarpCepWorkflowRuntimeService {

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
    public TaskReportDTO getReport(TaskReportParam param) {
        return null;
    }

    @Override
    public TaskValidateDTO validate(TaskValidateParam param) {
        return new TaskValidateDTO().setValid(true);
    }

    @Override
    public TaskRunDTO run(TaskRunParam param) {
        return null;
    }

    @Override
    public TaskCancelDTO cancel(TaskCancelParam param) {
        return null;
    }
}
