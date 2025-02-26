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
package cn.sliew.carp.module.dag.queue.handler;

import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationErrorHandler2 extends AbstractDagMessageHandler<Messages.ConfigurationError> {

    @Autowired
    private DagInstanceService dagInstanceService;

    @Override
    public Class<Messages.ConfigurationError> getMessageType() {
        return Messages.ConfigurationError.class;
    }

    @Override
    public void handle(Messages.ConfigurationError message) {
        if (message instanceof Messages.InvalidWorkflowId invalidExecution) {
            getLog().error("No such {} {} for {}",
                    message.getType(), message.getDagId(), message.getNamespace());
        } else {
            getLog().error("{} for {} {} for {}",
                    message.getClass().getSimpleName(),
                    message.getType(),
                    message.getDagId(),
                    message.getNamespace());
            dagInstanceService.updateStatus(message.getDagId(), null, ExecutionStatus.TERMINAL.name());
        }
    }
}
