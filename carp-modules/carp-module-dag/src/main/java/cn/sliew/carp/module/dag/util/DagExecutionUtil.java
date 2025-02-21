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
package cn.sliew.carp.module.dag.util;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.dag.model.ExecutionStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Objects;

public enum DagExecutionUtil {
    ;

    private static final EnumSet<ExecutionStatus> STAGE_FAILED_STATUS = EnumSet.of(
            ExecutionStatus.TERMINAL,
            ExecutionStatus.STOPPED,
            ExecutionStatus.CANCELED
    );

    private static final EnumSet<ExecutionStatus> STAGE_COMPLETE_STATUS = EnumSet.of(
            ExecutionStatus.SUCCEEDED,
            ExecutionStatus.FAILED_CONTINUE,
            ExecutionStatus.SKIPPED
    );

    public static boolean anyUpstreamStepsFailed(DAG<DagStepDTO> dag, DagStepDTO dagStepDTO) {
        return dag.inDegreeOf(findNode(dag, dagStepDTO)).stream()
                .anyMatch(s -> STAGE_FAILED_STATUS.contains(s.getStatus())
                        || (StringUtils.equalsIgnoreCase(s.getStatus(), ExecutionStatus.NOT_STARTED.name()) && anyUpstreamStepsFailed(dag, s)));
    }

    public static boolean allUpstreamStepsComplete(DAG<DagStepDTO> dag, DagStepDTO dagStepDTO) {
        return dag.inDegreeOf(findNode(dag, dagStepDTO)).stream()
                .allMatch(s -> STAGE_COMPLETE_STATUS.contains(s.getStatus())
                        && allUpstreamStepsComplete(dag, s));
    }

    private static DagStepDTO findNode(DAG<DagStepDTO> dag, DagStepDTO dagStepDTO) {
        return dag.nodes().stream().filter(s -> Objects.equals(s.getId(), dagStepDTO.getId())).findFirst().orElseThrow();
    }
}
