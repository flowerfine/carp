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

package cn.sliew.carp.module.workflow.api.service;

import cn.sliew.carp.framework.dag.service.dto.DagConfigComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.x6.graph.DagGraphVO;
import com.fasterxml.jackson.databind.JsonNode;

public interface WorkflowDagService {

    Long initialize(String name, JsonNode inputParams, String remark);

    void destroy(Long dagId);

    DagConfigComplexDTO getDag(Long dagId);

    DagConfigStepDTO getStep(Long stepId);

    void update(Long dagId, DagGraphVO graph);

    DagInstanceComplexDTO getDagInstance(Long dagInstanceId);
}
