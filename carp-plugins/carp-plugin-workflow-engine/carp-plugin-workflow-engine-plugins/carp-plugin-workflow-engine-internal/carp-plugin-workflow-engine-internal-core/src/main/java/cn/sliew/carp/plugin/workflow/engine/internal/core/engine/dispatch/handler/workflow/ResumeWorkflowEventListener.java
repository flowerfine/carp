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
package cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.handler.workflow;

import cn.sliew.carp.plugin.workflow.engine.internal.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.workflow.WorkflowInstanceEventDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResumeWorkflowEventListener implements InternalWorkflowEventListener<WorkflowInstanceEventDTO> {

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.COMMAND_RESUME;
    }

    @Override
    public void handle(WorkflowInstanceEventDTO event) {
        log.info("on event, {}", JacksonUtil.toJsonString(event));
    }
}
