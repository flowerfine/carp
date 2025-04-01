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
package cn.sliew.carp.module.workflow.api.service.impl;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.dag.service.*;
import cn.sliew.carp.framework.dag.service.dto.*;
import cn.sliew.carp.framework.dag.service.param.DagInstanceSimplePageParam;
import cn.sliew.carp.framework.dag.x6.dnd.X6EdgeDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6GraphDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodeDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodeDataDTO;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.workflow.api.manager.WorkflowInstanceManager;
import cn.sliew.carp.module.workflow.api.service.WorkflowInstanceService;
import cn.sliew.carp.module.workflow.api.service.convert.X6EdgeConvert;
import cn.sliew.carp.module.workflow.api.service.convert.X6NodeTaskConvert;
import cn.sliew.carp.module.workflow.api.service.dto.X6WorkflowStepInstanceData;
import cn.sliew.carp.module.workflow.api.service.dto.X6WorkflowTaskInstanceData;
import cn.sliew.carp.module.workflow.api.service.param.WorkflowRunParam;
import cn.sliew.carp.module.workflow.api.service.param.WorkflowStopParam;
import cn.sliew.carp.module.workflow.domain.convert.*;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepOrder;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkflowInstanceServiceImpl implements WorkflowInstanceService {

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagConfigStepService dagConfigStepService;
    @Autowired
    private DagConfigLinkService dagConfigLinkService;
    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagStepTaskService dagStepTaskService;
    @Autowired
    private WorkflowInstanceManager workflowInstanceManager;

    @Override
    public PageResult<WorkflowInstance> page(DagInstanceSimplePageParam param) {
        PageResult<DagInstanceDTO> pageResult = dagInstanceComplexService.page(param);
        return PageUtil.buildPageResult(pageResult, WorkflowInstanceConvert.INSTANCE::toDto);
    }

    @Override
    public WorkflowInstance get(Long workflowInstanceId) {
        DagInstanceDTO dagInstanceDTO = dagInstanceComplexService.selectSimpleOne(workflowInstanceId);
        return WorkflowInstanceConvert.INSTANCE.toDto(dagInstanceDTO);
    }

    @Override
    public WorkflowInstance getGraph(Long workflowInstanceId) {
        DagInstanceComplexDTO complexDTO = dagInstanceComplexService.selectOne(workflowInstanceId);
        WorkflowInstance dto = WorkflowInstanceConvert.INSTANCE.toDto(complexDTO);
        WorkflowExecutionGraph graph = new WorkflowExecutionGraph();
        dto.setGraph(graph);

        List<WorkflowStepInstance> allNodes = WorkflowStepInstanceConvert.INSTANCE.toDto(complexDTO.getSteps());
        WorkflowStepInstance preNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepOrder() == CarpWorkflowStepOrder.PRE).findFirst().orElse(null);
        WorkflowStepInstance postNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepOrder() == CarpWorkflowStepOrder.POST).findFirst().orElse(null);
        List<WorkflowStepInstance> normalNodes = allNodes.stream().filter(node -> node.getNode().getMeta().getStepOrder() == CarpWorkflowStepOrder.NORMAL).collect(Collectors.toList());
        graph.setPreTask(preNode);
        graph.setPostTask(postNode);
        graph.setTasks(normalNodes);

        List<DagConfigLinkDTO> linkDTOS = dagConfigLinkService.listLinks(complexDTO.getDagConfig().getId());
        graph.setEdges(WorkflowDefinitionGraphEdgeConvert.INSTANCE.toDto(linkDTOS));

        return dto;
    }

    @Override
    public X6GraphDTO getX6Graph(Long workflowInstanceId) {
        WorkflowInstance dto = getGraph(workflowInstanceId);
        WorkflowExecutionGraph graph = dto.getGraph();

        List<X6EdgeDTO> edges = X6EdgeConvert.INSTANCE.toDto(graph.getEdges()).stream().map(edge -> {
            return X6EdgeDTO.builder()
                    .id(edge.getId())
                    .shape("cicd-edge")
                    .source(edge.getSource())
                    .target(edge.getTarget())
                    .data(edge.getData())
                    .build();
        }).collect(Collectors.toList());

        Map<String, X6NodeDTO> nodeMap = graph.getTasks().stream()
                .map(task -> X6NodeTaskConvert.INSTANCE.toDto(task))
                .collect(Collectors.toMap(X6NodeDTO::getId, dto1 -> dto1));

        // 重写了一下 shape
        List<X6NodeDTO> nodes = new ArrayList<>();
        for (WorkflowStepInstance stepInstance : graph.getTasks()) {
            List<WorkflowTaskInstance> taskInstances = listTasks(stepInstance.getId());
            List<X6WorkflowTaskInstanceData> tasks = taskInstances.stream().map(taskInstance -> {
                return X6WorkflowTaskInstanceData.builder()
                        .name(taskInstance.getName())
                        .status(taskInstance.getStatus())
                        .startTime(taskInstance.getStartTime())
                        .endTime(taskInstance.getEndTime())
                        .build();
            }).collect(Collectors.toList());
            X6WorkflowStepInstanceData stepInstanceData = X6WorkflowStepInstanceData.builder()
                    .status(stepInstance.getStatus())
                    .startTime(stepInstance.getStartTime())
                    .endTime(stepInstance.getEndTime())
                    .outputs(stepInstance.getOutputs())
                    .tasks(tasks)
                    .build();
            // 塞进去
            X6NodeDTO x6NodeDTO = nodeMap.get(stepInstance.getNode().getStepId());
            nodes.add(X6NodeDTO.builder()
                    .id(x6NodeDTO.getId())
                    .shape("cicd-node")
                    .position(x6NodeDTO.getPosition())
                    .ports(x6NodeDTO.getPorts())
                    .data(X6NodeDataDTO.builder()
                            .label(x6NodeDTO.getData().getLabel())
                            .meta(x6NodeDTO.getData().getMeta())
                            .attrs(x6NodeDTO.getData().getAttrs())
                            .extData(stepInstanceData)
                            .build())
                    .build());
        }
        return X6GraphDTO.builder()
                .edges(edges)
                .nodes(nodes)
                .build();
    }

    @Override
    public WorkflowStepInstance getStep(Long workflowStepInstanceId) {
        DagStepDTO dagStepDTO = dagStepService.get(workflowStepInstanceId);
        DagConfigStepDTO dagConfigStepDTO = dagConfigStepService.get(dagStepDTO.getDagConfigStep().getId());
        WorkflowStepInstance stepInstance = WorkflowStepInstanceConvert.INSTANCE.toDto(dagStepDTO);
        stepInstance.setNode(WorkflowDefinitionGraphNodeConvert.INSTANCE.toDto(dagConfigStepDTO));
        WorkflowInstance workflowInstance = get(stepInstance.getWorkflowInstance().getId());
        stepInstance.setWorkflowInstance(workflowInstance);
        return stepInstance;
    }

    @Override
    public WorkflowTaskInstance getTask(Long workflowTaskInstanceId) {
        DagStepTaskDTO dagStepTaskDTO = dagStepTaskService.get(workflowTaskInstanceId);
        return WorkflowStepTaskInstanceConvert.INSTANCE.toDto(dagStepTaskDTO);
    }

    @Override
    public List<WorkflowTaskInstance> listTasks(Long workflowStepInstanceId) {
        List<DagStepTaskDTO> dtos = dagStepTaskService.listTasks(workflowStepInstanceId);
        return WorkflowStepTaskInstanceConvert.INSTANCE.toDto(dtos);
    }

    @Override
    public void addTask(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        DagStepTaskDTO dagStepTaskDTO = WorkflowStepTaskInstanceConvert.INSTANCE.toDo(taskInstance);
        dagStepTaskService.add(dagStepTaskDTO);
    }

    @Override
    public void updateTask(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        DagStepTaskDTO dagStepTaskDTO = WorkflowStepTaskInstanceConvert.INSTANCE.toDo(taskInstance);
        dagStepTaskService.update(dagStepTaskDTO);
    }

    @Override
    public Long simpleInitialize(Long workflowDefinitionId) {
        return dagInstanceComplexService.initialize(workflowDefinitionId);
    }

    @Override
    public Long run(WorkflowRunParam param) {
        Long workflowInstanceId = simpleInitialize(param.getId());
        workflowInstanceManager.deploy(workflowInstanceId, param.getGlobalVariable());
        return workflowInstanceId;
    }

    @Override
    public void stop(WorkflowStopParam param) {

    }
}
