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

package cn.sliew.carp.module.workflow.internal.listener.workflowinstance;

import cn.sliew.carp.module.queue.api.MessageListener;
import cn.sliew.carp.module.workflow.internal.statemachine.WorkflowInstanceStateMachine;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageListener(topic = WorkflowInstanceResumeEventListener.TOPIC, consumerGroup = WorkflowInstanceStateMachine.CONSUMER_GROUP)
public class WorkflowInstanceResumeEventListener implements WorkflowInstanceEventListener {

    public static final String TOPIC = "TOPIC_WORKFLOW_INSTANCE_COMMAND_RESUME";

    @Override
    public void onEvent(WorkflowInstanceEventDTO event) {
        log.info("on event, {}", JacksonUtil.toJsonString(event));
    }
}
