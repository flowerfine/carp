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
package cn.sliew.carp.plugin.workflow.engine.internal.stage.model.task;

import cn.sliew.carp.plugin.workflow.engine.internal.domain.ExecutionStatus;
import lombok.Getter;

import java.util.*;

@Getter
@SuppressWarnings("FallThrough")
public final class TaskResult {

    public static final TaskResult SUCCEEDED = TaskResult.ofStatus(ExecutionStatus.SUCCEEDED);
    public static final TaskResult RUNNING = TaskResult.ofStatus(ExecutionStatus.RUNNING);
    public static final TaskResult TERMINAL = TaskResult.ofStatus(ExecutionStatus.TERMINAL);

    private final ExecutionStatus status;

    /**
     * Step-scoped data.
     */
    private final Map<String, ?> context;

    /**
     * Workflow-scoped data.
     */
    private final Map<String, ?> outputs;

    TaskResult(ExecutionStatus status, Map<String, ?> context, Map<String, ?> outputs) {
        this.status = status;
        this.context = context;
        this.outputs = outputs;
    }

    public static TaskResult ofStatus(ExecutionStatus status) {
        return TaskResult.builder(status).build();
    }

    public static TaskResultBuilder builder(ExecutionStatus status) {
        return new TaskResultBuilder().status(status);
    }

    public static class TaskResultBuilder {
        private ExecutionStatus status;
        private ArrayList<String> context$key;
        private ArrayList<Object> context$value;
        private ArrayList<String> outputs$key;
        private ArrayList<Object> outputs$value;

        TaskResultBuilder() {
        }

        public TaskResultBuilder status(ExecutionStatus status) {
            this.status = status;
            return this;
        }

        public TaskResultBuilder context(String contextKey, Object contextValue) {
            if (this.context$key == null) {
                this.context$key = new ArrayList();
                this.context$value = new ArrayList();
            }

            this.context$key.add(contextKey);
            this.context$value.add(contextValue);
            return this;
        }

        public TaskResultBuilder context(Map<? extends String, ?> context) {
            if (context == null) {
                throw new NullPointerException("context cannot be null");
            } else {
                if (this.context$key == null) {
                    this.context$key = new ArrayList();
                    this.context$value = new ArrayList();
                }

                for (Map.Entry<? extends String, ?> entry : context.entrySet()) {
                    this.context$key.add(entry.getKey());
                    this.context$value.add(entry.getValue());
                }

                return this;
            }
        }

        public TaskResultBuilder clearContext() {
            if (this.context$key != null) {
                this.context$key.clear();
                this.context$value.clear();
            }

            return this;
        }

        public TaskResultBuilder output(String outputKey, Object outputValue) {
            if (this.outputs$key == null) {
                this.outputs$key = new ArrayList();
                this.outputs$value = new ArrayList();
            }

            this.outputs$key.add(outputKey);
            this.outputs$value.add(outputValue);
            return this;
        }

        public TaskResultBuilder outputs(Map<? extends String, ?> outputs) {
            if (outputs == null) {
                throw new NullPointerException("outputs cannot be null");
            } else {
                if (this.outputs$key == null) {
                    this.outputs$key = new ArrayList();
                    this.outputs$value = new ArrayList();
                }

                for (Map.Entry<? extends String, ?> entry : outputs.entrySet()) {
                    this.outputs$key.add(entry.getKey());
                    this.outputs$value.add(entry.getValue());
                }

                return this;
            }
        }

        public TaskResultBuilder clearOutputs() {
            if (this.outputs$key != null) {
                this.outputs$key.clear();
                this.outputs$value.clear();
            }

            return this;
        }

        public TaskResult build() {
            Map context;
            switch (this.context$key == null ? 0 : this.context$key.size()) {
                case 0:
                    context = Collections.emptyMap();
                    break;
                case 1:
                    context = Collections.singletonMap(this.context$key.get(0), this.context$value.get(0));
                    break;
                default:
                    Map<String, Object> internalContext = new LinkedHashMap(this.context$key.size() < 1073741824 ? 1 + this.context$key.size() + (this.context$key.size() - 3) / 3 : Integer.MAX_VALUE);

                    for(int $i = 0; $i < this.context$key.size(); ++$i) {
                        internalContext.put(this.context$key.get($i), this.context$value.get($i));
                    }

                    context = Collections.unmodifiableMap(internalContext);
            }

            Map outputs;
            switch (this.outputs$key == null ? 0 : this.outputs$key.size()) {
                case 0:
                    outputs = Collections.emptyMap();
                    break;
                case 1:
                    outputs = Collections.singletonMap(this.outputs$key.get(0), this.outputs$value.get(0));
                    break;
                default:
                    Map<String, Object> internalOutputs = new LinkedHashMap(this.outputs$key.size() < 1073741824 ? 1 + this.outputs$key.size() + (this.outputs$key.size() - 3) / 3 : Integer.MAX_VALUE);

                    for(int $i = 0; $i < this.outputs$key.size(); ++$i) {
                        internalOutputs.put(this.outputs$key.get($i), this.outputs$value.get($i));
                    }

                    outputs = Collections.unmodifiableMap(internalOutputs);
            }

            return new TaskResult(this.status, context, outputs);
        }
    }
}
