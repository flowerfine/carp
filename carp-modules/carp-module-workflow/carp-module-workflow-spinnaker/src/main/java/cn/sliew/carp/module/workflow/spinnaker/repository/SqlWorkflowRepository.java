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
package cn.sliew.carp.module.workflow.spinnaker.repository;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowStepOrder;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.*;
import cn.sliew.carp.framework.dag.service.dto.*;
import cn.sliew.carp.module.workflow.domain.ExecutionStatus;
import cn.sliew.carp.module.workflow.domain.convert.WorkflowDefinitionGraphEdgeConvert;
import cn.sliew.carp.module.workflow.domain.convert.WorkflowInstanceConvert;
import cn.sliew.carp.module.workflow.domain.convert.WorkflowStepInstanceConvert;
import cn.sliew.carp.module.workflow.domain.convert.WorkflowStepTaskInstanceConvert;
import cn.sliew.carp.module.workflow.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.repository.WorkflowRepository;
import cn.sliew.carp.module.workflow.stage.model.util.WorkflowUtil;
import cn.sliew.milky.common.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SqlWorkflowRepository implements WorkflowRepository {

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
        WorkflowStepInstance preNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepOrder() == CarpWorkflowStepOrder.PRE).findFirst().orElse(null);
        WorkflowStepInstance postNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepOrder() == CarpWorkflowStepOrder.POST).findFirst().orElse(null);
        List<WorkflowStepInstance> normalNodes = allNodes.stream().filter(node -> node.getNode().getMeta().getStepOrder() == CarpWorkflowStepOrder.NORMAL).collect(Collectors.toList());
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
    public void prepareRun(Long workflowInstanceId,
                           Map<String, Object> inputs,
                           Map<String, Map<String, Object>> stepInputs) {

        // 更新 dag_instance
        DagInstanceDTO dagInstanceDTO = new DagInstanceDTO();
        dagInstanceDTO.setId(workflowInstanceId);
        if (CollectionUtils.isEmpty(inputs) == false) {
            dagInstanceDTO.setInputs(JacksonUtil.toJsonNode(inputs));
        }
        dagInstanceDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
        dagInstanceService.update(dagInstanceDTO);

        // 更新 dag_step
        List<DagStepDTO> steps = dagStepService.listSteps(workflowInstanceId);
        if (CollectionUtils.isEmpty(steps) == false) {
            for (DagStepDTO dagStepDTO : steps) {
                if (CollectionUtils.isEmpty(stepInputs) == false
                        && stepInputs.containsKey(dagStepDTO.getDagConfigStep().getStepId())) {
                    dagStepDTO.setInputs(JacksonUtil.toJsonNode(stepInputs.get(dagStepDTO.getDagConfigStep().getStepId())));
                }
                dagStepDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
                dagStepService.update(dagStepDTO);
            }
        }
        // 更新 dag_link
        List<DagLinkDTO> links = dagLinkService.listLinks(workflowInstanceId);
        if (CollectionUtils.isEmpty(links) == false) {
            for (DagLinkDTO dagLinkDTO : links) {
                dagLinkDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
                dagLinkService.update(dagLinkDTO);
            }
        }
    }

    @Override
    public void update(WorkflowInstance workflowInstance) {
        DagInstanceDTO dagInstanceDTO = WorkflowInstanceConvert.INSTANCE.toDo(workflowInstance);
        dagInstanceService.update(dagInstanceDTO);
    }

    @Override
    public List<WorkflowStepInstance> getStepInstances(Long workflowInstanceId) {
        List<DagStepDTO> steps = dagStepService.listSteps(workflowInstanceId);
        return WorkflowStepInstanceConvert.INSTANCE.toDto(steps);
    }

    @Override
    public WorkflowStepInstance getStepInstance(Long stepInstanceId) {
        DagStepDTO dagStepDTO = dagStepService.getWithConfig(stepInstanceId);
        WorkflowStepInstance stepInstance = WorkflowStepInstanceConvert.INSTANCE.toDto(dagStepDTO);
        stepInstance.setWorkflowInstance(get(stepInstance.getWorkflowInstance().getId()));
        return stepInstance;
    }

    @Override
    public void updateStepInstance(WorkflowStepInstance stepInstance) {
        DagStepDTO dagStepDTO = WorkflowStepInstanceConvert.INSTANCE.toDo(stepInstance);
        dagStepService.update(dagStepDTO);
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
        DagStepTaskDTO dagStepTaskDTO = convertToTask(stepInstance, taskExecution);
        dagStepTaskService.add(dagStepTaskDTO);
    }

    @Override
    public void updateStepTaskInstance(WorkflowStepInstance stepInstance, TaskExecutionImpl taskExecution) {
        DagStepTaskDTO dagStepTaskDTO = convertToTask(stepInstance, taskExecution);
        dagStepTaskService.update(dagStepTaskDTO);
    }

    private DagStepTaskDTO convertToTask(WorkflowStepInstance stepInstance, TaskExecutionImpl taskExecution) {
        DagStepTaskDTO dagStepTaskDTO = WorkflowStepTaskInstanceConvert.INSTANCE.toDo(taskExecution);
        dagStepTaskDTO.setNamespace(stepInstance.getNamespace());
        dagStepTaskDTO.setDagInstanceId(stepInstance.getWorkflowInstance().getId());
        dagStepTaskDTO.setDagStepId(stepInstance.getId());
        return dagStepTaskDTO;
    }
}
