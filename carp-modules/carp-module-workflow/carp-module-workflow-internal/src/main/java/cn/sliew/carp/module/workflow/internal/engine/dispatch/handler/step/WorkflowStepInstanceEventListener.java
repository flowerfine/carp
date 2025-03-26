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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.handler.step;

import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowStepInstanceStatusEvent;
import cn.sliew.carp.module.workflow.api.engine.dispatch.handler.WorkflowStepInstanceEventHandler;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowStepInstanceEventDTO;

public interface WorkflowStepInstanceEventListener extends WorkflowStepInstanceEventHandler {

    @Override
    default void handle(WorkflowStepInstanceStatusEvent event) {
        if (event instanceof WorkflowStepInstanceEventDTO eventDTO) {
            handleInternal(eventDTO);
            return;
        }
        throw new RuntimeException();
    }

    void handleInternal(WorkflowStepInstanceEventDTO eventDTO);
}
