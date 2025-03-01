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

import cn.sliew.carp.framework.common.model.BaseBuilderDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinitionGraphNode;
import jakarta.annotation.Nonnull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class WorkflowStepInstance extends BaseBuilderDTO {

    private String namespace;

    private WorkflowInstance workflowInstance;

    private WorkflowDefinitionGraphNode node;

    private String uuid;

    private WorkflowStepInstanceBody body;

    private Map<String, Object> inputs;

    private Map<String, Object> outputs;

    private String status;

    private Date startTime;

    private Date endTime;

    private WorkflowStepContext context;

    public void setContext(@Nonnull Map<String, Object> context) {
        if (context instanceof WorkflowStepContext stepContext) {
            this.context = stepContext;
        } else {
            this.context = new WorkflowStepContext(this, context);
        }
    }
}
