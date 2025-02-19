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

import cn.sliew.carp.framework.dag.x6.dnd.DndDTO;
import cn.sliew.carp.framework.dag.x6.dnd.DndPortDTO;
import cn.sliew.carp.framework.dag.x6.dnd.DndPortGroupEnum;
import cn.sliew.carp.module.workflow.api.dag.dnd.WorkflowDefinitionNodeDndDTO;
import cn.sliew.carp.module.workflow.api.service.WorkflowDagService;
import cn.sliew.module.workflow.stage.model.StageDefinition;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkflowDagServiceImpl implements WorkflowDagService {

    @Autowired
    private List<StageDefinition> stageDefinitions;

    @Override
    public List<DndDTO> getDnds() {
        return loadWorkflowPanel();
    }

    private List<DndDTO> loadWorkflowPanel() {
        Map<String, List<StageDefinition>> categoryMap = stageDefinitions.stream()
                .collect(Collectors.groupingBy(StageDefinition::getCategory));
        return categoryMap.entrySet().stream()
                .map(entry -> buildDemoDndDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private WorkflowDefinitionNodeDndDTO buildDemoDndDTO(String category, List<StageDefinition> stageDefinitions) {
        WorkflowDefinitionNodeDndDTO categoryDTO = new WorkflowDefinitionNodeDndDTO();
        categoryDTO.setKey(category);
        categoryDTO.setTitle(category);
        categoryDTO.setDocString(category);
        categoryDTO.setIsLeaf(false);
        categoryDTO.setChildren(Lists.newArrayList());

        for (StageDefinition stageDefinition : stageDefinitions) {
            WorkflowDefinitionNodeDndDTO child = new WorkflowDefinitionNodeDndDTO();
            child.setCategory(categoryDTO.getKey());
            child.setKey(stageDefinition.getType());
            child.setTitle(stageDefinition.getType());
            child.setDocString(stageDefinition.getRemark());
            child.setIsLeaf(true);
            child.setPorts(getSourcePorts(child.getKey()));
            categoryDTO.getChildren().add(child);
        }
        return categoryDTO;
    }

    private List<DndPortDTO> getSourcePorts(String key) {
        List<DndPortDTO> ports = new ArrayList<>();
        DndPortDTO portDTO = new DndPortDTO();
        portDTO.setId(key + "-" + DndPortGroupEnum.bottom.name());
        portDTO.setGroup(DndPortGroupEnum.bottom.name());
        ports.add(portDTO);
        return ports;
    }

    private List<DndPortDTO> getSinkPorts(String key) {
        List<DndPortDTO> ports = new ArrayList<>();
        DndPortDTO portDTO = new DndPortDTO();
        portDTO.setId(key + "-" + DndPortGroupEnum.top.name());
        portDTO.setGroup(DndPortGroupEnum.top.name());
        ports.add(portDTO);
        return ports;
    }

    private List<DndPortDTO> getTransformPorts(String key) {
        List<DndPortDTO> ports = new ArrayList<>();
        DndPortDTO sourcePort = new DndPortDTO();
        sourcePort.setId(key + "-" + DndPortGroupEnum.top.name());
        sourcePort.setGroup(DndPortGroupEnum.top.name());
        ports.add(sourcePort);

        DndPortDTO sinkPort = new DndPortDTO();
        sinkPort.setId(key + "-" + DndPortGroupEnum.bottom.name());
        sinkPort.setGroup(DndPortGroupEnum.bottom.name());
        ports.add(sinkPort);
        return ports;
    }
}
