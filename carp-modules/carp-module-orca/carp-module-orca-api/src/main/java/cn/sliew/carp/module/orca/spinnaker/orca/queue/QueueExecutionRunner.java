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
package cn.sliew.carp.module.orca.spinnaker.orca.queue;

import cn.sliew.carp.module.orca.spinnaker.api.executions.ExecutionRunner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;

public class QueueExecutionRunner implements ExecutionRunner {

    private Queue queue;

    public QueueExecutionRunner(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void start(PipelineExecution execution) {
        queue.push(new Messages.StartExecution(execution));
    }

    @Override
    public void reschedule(PipelineExecution execution) {
        queue.push(new Messages.RescheduleExecution(execution));
    }

    @Override
    public void restart(PipelineExecution execution, Long stageId) {
        queue.push(new Messages.RestartStage(execution, stageId, null));
    }

    @Override
    public void unpause(PipelineExecution execution) {
        queue.push(new Messages.ResumeExecution(execution));
    }

    @Override
    public void cancel(PipelineExecution execution, String reason) {
        queue.push(new Messages.CancelExecution(execution, null, reason));
    }
}
