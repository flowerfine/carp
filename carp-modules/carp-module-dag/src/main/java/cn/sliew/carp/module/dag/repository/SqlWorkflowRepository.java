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
package cn.sliew.carp.module.dag.repository;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowStepType;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.*;
import cn.sliew.carp.framework.dag.service.dto.*;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowDefinitionGraphEdgeConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowInstanceConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowStepInstanceConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowStepTaskInstanceConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.repository.WorkflowRepository;
import cn.sliew.carp.module.workflow.stage.model.util.WorkflowUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class SqlWorkflowRepository implements WorkflowRepository {

    @Autowired
    private DagConfigComplexService dagConfigComplexService;
    @Autowired
    private DagConfigLinkService dagConfigLinkService;

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagLinkService dagLinkService;
    @Autowired
    private DagStepTaskService dagStepTaskService;

    @Override
    public WorkflowDefinition getWorkflowDefinition(Long id) {
        return null;
    }

    @Override
    public WorkflowInstance get(Long id) {
        DagInstanceComplexDTO complexDTO = dagInstanceComplexService.selectOne(id);
        WorkflowInstance workflowInstance = WorkflowInstanceConvert.INSTANCE.toDto(complexDTO);
        WorkflowExecutionGraph graph = new WorkflowExecutionGraph();

        List<WorkflowStepInstance> allNodes = WorkflowStepInstanceConvert.INSTANCE.toDto(complexDTO.getSteps());
        WorkflowStepInstance preNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == CarpWorkflowStepType.PRE).findFirst().orElse(null);
        WorkflowStepInstance postNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == CarpWorkflowStepType.POST).findFirst().orElse(null);
        List<WorkflowStepInstance> normalNodes = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == CarpWorkflowStepType.NORMAL).collect(Collectors.toList());
        graph.setPreTask(preNode);
        graph.setPostTask(postNode);
        graph.setTasks(normalNodes);
        List<DagConfigLinkDTO> linkDTOS = dagConfigLinkService.listLinks(complexDTO.getDagConfig().getId());
        graph.setEdges(WorkflowDefinitionGraphEdgeConvert.INSTANCE.toDto(linkDTOS));

        workflowInstance.setGraph(graph);
        return workflowInstance;
    }

    @Override
    public DAG<WorkflowStepInstance> getDAG(Long id) {
        DagInstanceComplexDTO complexDTO = dagInstanceComplexService.selectOne(id);
        return WorkflowUtil.buildDag(complexDTO);
    }

    @Override
    public Long addFromDefinition(Long workflowDefinitionId) {
        // inputs 处理
        DagConfigComplexDTO dagConfigComplexDTO = dagConfigComplexService.selectOne(workflowDefinitionId);
        List<DagConfigStepDTO> steps = dagConfigComplexDTO.getSteps();
        List<DagConfigLinkDTO> links = dagConfigComplexDTO.getLinks();
        dagConfigComplexDTO.setSteps(null);
        dagConfigComplexDTO.setLinks(null);

        // 插入 dag_instance
        DagInstanceDTO dagInstanceDTO = new DagInstanceDTO();
        dagInstanceDTO.setNamespace(dagConfigComplexDTO.getNamespace());
        dagInstanceDTO.setDagConfig(dagConfigComplexDTO);
        dagInstanceDTO.setUuid(UUIDUtil.randomUUId());
//        dagInstanceDTO.setBody(JacksonUtil.toJsonNode(dagConfigComplexDTO));
        dagInstanceDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
        Long dagInstanceId = dagInstanceService.add(dagInstanceDTO);
        dagInstanceDTO.setId(dagInstanceId);
        // 插入 dag_step
        if (CollectionUtils.isEmpty(steps) == false) {
            for (DagConfigStepDTO dagConfigStepDTO : steps) {
                DagStepDTO dagStepDTO = new DagStepDTO();
                dagStepDTO.setNamespace(dagConfigComplexDTO.getNamespace());
                dagStepDTO.setDagInstance(dagInstanceDTO);
                dagStepDTO.setDagConfigStep(dagConfigStepDTO);
                dagStepDTO.setUuid(UUIDUtil.randomUUId());
//                dagStepDTO.setBody(JacksonUtil.toJsonNode(dagConfigStepDTO));
                dagStepDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
                dagStepService.add(dagStepDTO);
            }
        }
        // 插入 dag_link
        if (CollectionUtils.isEmpty(links) == false) {
            for (DagConfigLinkDTO dagConfigLinkDTO : links) {
                DagLinkDTO dagLinkDTO = new DagLinkDTO();
                dagLinkDTO.setNamespace(dagConfigComplexDTO.getNamespace());
                dagLinkDTO.setDagInstance(dagInstanceDTO);
                dagLinkDTO.setDagConfigLink(dagConfigLinkDTO);
                dagLinkDTO.setUuid(UUIDUtil.randomUUId());
                dagLinkDTO.setInputs(dagConfigLinkDTO.getLinkAttrs());
//                dagLinkDTO.setBody(JacksonUtil.toJsonNode(dagConfigLinkDTO));
                dagLinkDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
                dagLinkService.add(dagLinkDTO);
            }
        }
        return dagInstanceId;
    }

    @Override
    public WorkflowStepInstance getStepInstance(Long stepInstanceId) {
        DagStepDTO dagStepDTO = dagStepService.getWithConfig(stepInstanceId);
        return WorkflowStepInstanceConvert.INSTANCE.toDto(dagStepDTO);
    }

    @Override
    public List<TaskExecutionImpl> getStepTaskInstances(Long stepInstanceId) {
        List<DagStepTaskDTO> dagStepTaskDTOS = dagStepTaskService.listTasks(stepInstanceId);
        return WorkflowStepTaskInstanceConvert.INSTANCE.toDto(dagStepTaskDTOS);
    }

    @Override
    public TaskExecutionImpl getStepTaskInstance(Long stepTaskInstanceId) {
        DagStepTaskDTO dagStepTaskDTO = dagStepTaskService.get(stepTaskInstanceId);
        return WorkflowStepTaskInstanceConvert.INSTANCE.toDto(dagStepTaskDTO);
    }

    @Override
    public TaskExecutionImpl getStepTaskInstance(Long workflowInstanceId, Long stepInstanceId, Long taskId) {
        DagStepTaskDTO dagStepTaskDTO = dagStepTaskService.get(workflowInstanceId, stepInstanceId, taskId);
        return WorkflowStepTaskInstanceConvert.INSTANCE.toDto(dagStepTaskDTO);
    }

    @Override
    public void addStepTaskInstance(WorkflowStepInstance stepInstance, TaskExecutionImpl taskExecution) {
        DagStepTaskDTO dagStepTaskDTO = new DagStepTaskDTO();
        BeanUtils.copyProperties(taskExecution, dagStepTaskDTO);
        dagStepTaskDTO.setId(null);
        dagStepTaskDTO.setNamespace(stepInstance.getNamespace());
        dagStepTaskDTO.setDagInstanceId(stepInstance.getWorkflowInstance().getId());
        dagStepTaskDTO.setDagStepId(stepInstance.getId());
        if (Objects.nonNull(taskExecution.getStatus())) {
            dagStepTaskDTO.setStatus(taskExecution.getStatus().name());
        }
        if (Objects.nonNull(taskExecution.getStartTime())) {
            dagStepTaskDTO.setStartTime(Date.from(taskExecution.getStartTime()));
        }
        if (Objects.nonNull(taskExecution.getEndTime())) {
            dagStepTaskDTO.setEndTime(Date.from(taskExecution.getEndTime()));
        }
        dagStepTaskService.add(dagStepTaskDTO);
    }
}
