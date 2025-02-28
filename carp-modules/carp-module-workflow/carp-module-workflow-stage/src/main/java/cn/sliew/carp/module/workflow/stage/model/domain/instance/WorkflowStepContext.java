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
package cn.sliew.carp.module.workflow.stage.model.domain.instance;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowExecutionGraphConvert;
import com.google.common.collect.ForwardingMap;
import org.apache.commons.collections4.MapUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowStepContext extends ForwardingMap<String, Object> implements Map<String, Object> {

    private final WorkflowStepInstance stepInstance;
    private final Map<String, Object> delegate;

    public WorkflowStepContext(WorkflowStepInstance stepInstance) {
        this(stepInstance, new HashMap<>());
    }

    public WorkflowStepContext(WorkflowStepContext stepContext) {
        this(stepContext.stepInstance, new HashMap<>(stepContext.delegate));
    }

    public WorkflowStepContext(WorkflowStepInstance stepInstance, Map<String, Object> delegate) {
        this.stepInstance = stepInstance;
        this.delegate = delegate;
        // todo evaluate inputs
        if (MapUtils.isNotEmpty(stepInstance.getInputs())) {
            delegate.putAll(stepInstance.getInputs());
        }
    }

    @Override
    protected Map<String, Object> delegate() {
        return delegate;
    }

    @Override
    public Object get(@Nullable Object key) {
        if (delegate().containsKey(key)) {
            return super.get(key);
        }
        DAG<WorkflowStepInstance> dag = WorkflowExecutionGraphConvert.INSTANCE.toDto(stepInstance.getWorkflowInstance().getGraph());

        return dag.getAncestors(stepInstance).stream()
                .filter(it -> it.getOutputs().containsKey(key))
                .findFirst()
                .map(it -> it.getOutputs().get(key))
                .orElse(null);
    }

    /**
     * Get a value from the current context ONLY - never looking at the ancestors' outputs
     *
     * @param key          The key to look
     * @param defaultValue default value to return if key is not present
     * @return value or null if not present
     */
    public Object getCurrentOnly(@Nullable Object key, Object defaultValue) {
        return super.getOrDefault(key, defaultValue);
    }

    /*
     * Gets all objects matching 'key', sorted by proximity to the current stage.
     * If the key exists in the current context, it will be the first element returned
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> getAll(Object key) {
        DAG<WorkflowStepInstance> dag = WorkflowExecutionGraphConvert.INSTANCE.toDto(stepInstance.getWorkflowInstance().getGraph());
        List<E> result =
                (List<E>)
                        dag.getAncestors(stepInstance).stream()
                                .filter(it -> it.getOutputs().containsKey(key))
                                .map(it -> it.getOutputs().get(key))
                                .collect(Collectors.toList());

        if (delegate.containsKey(key)) {
            result.add(0, (E) delegate.get(key));
        }
        return result;
    }
}
