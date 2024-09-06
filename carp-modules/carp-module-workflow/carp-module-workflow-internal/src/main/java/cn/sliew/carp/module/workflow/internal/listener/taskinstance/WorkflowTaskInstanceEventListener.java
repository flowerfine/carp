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

package cn.sliew.carp.module.workflow.internal.listener.taskinstance;

import cn.sliew.scaleph.queue.Message;
import cn.sliew.scaleph.queue.MessageHandler;
import cn.sliew.scaleph.queue.util.FuryUtil;

public interface WorkflowTaskInstanceEventListener extends MessageHandler {

    @Override
    default void handler(Message message) throws Exception {
        if (message.getBody() != null) {
            Object deserialized = FuryUtil.deserializeByJava(message.getBody());
            if (deserialized instanceof WorkflowTaskInstanceEventDTO) {
                WorkflowTaskInstanceEventDTO eventDTO = (WorkflowTaskInstanceEventDTO)deserialized;
                onEvent(eventDTO);
            }
        }
    }

    void onEvent(WorkflowTaskInstanceEventDTO eventDTO);
}
