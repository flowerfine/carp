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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowDagServiceImpl implements WorkflowDagService {

    @Override
    public List<DndDTO> getDnds() {
        return loadWorkflowPanel();
    }

    private List<DndDTO> loadWorkflowPanel() {
        List<DndDTO> dnds = new ArrayList();
        dnds.add(buildDemoDndDTO());
        return dnds;
    }

    private WorkflowDefinitionNodeDndDTO buildDemoDndDTO() {
        WorkflowDefinitionNodeDndDTO category = new WorkflowDefinitionNodeDndDTO();
        category.setKey("demo-category-key");
        category.setTitle("demo-category-1");
        category.setDocString("a demo workflow node category");
        category.setIsLeaf(false);
        List<DndDTO> children = new ArrayList();

        WorkflowDefinitionNodeDndDTO childLogDemo = new WorkflowDefinitionNodeDndDTO();;
        childLogDemo.setCategory(category.getKey());
        childLogDemo.setKey("demo-log-key");
        childLogDemo.setTitle("demo-log");
        childLogDemo.setDocString("a demo workflow log node");
        childLogDemo.setIsLeaf(true);
        childLogDemo.setPorts(getSourcePorts(childLogDemo.getKey()));
        children.add(childLogDemo);

        WorkflowDefinitionNodeDndDTO childWaitDemo = new WorkflowDefinitionNodeDndDTO();;
        childWaitDemo.setCategory(category.getKey());
        childWaitDemo.setKey("demo-wait-key");
        childWaitDemo.setTitle("demo-wait");
        childWaitDemo.setDocString("a demo workflow wait node");
        childWaitDemo.setIsLeaf(true);
        childWaitDemo.setPorts(getTransformPorts(childWaitDemo.getKey()));
        children.add(childWaitDemo);

        WorkflowDefinitionNodeDndDTO childPrintDemo = new WorkflowDefinitionNodeDndDTO();;
        childPrintDemo.setCategory(category.getKey());
        childPrintDemo.setKey("demo-print-key");
        childPrintDemo.setTitle("demo-print");
        childPrintDemo.setDocString("a demo workflow print node");
        childPrintDemo.setIsLeaf(true);
        childPrintDemo.setPorts(getSinkPorts(childPrintDemo.getKey()));
        children.add(childPrintDemo);

        category.setChildren(children);
        return category;
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
