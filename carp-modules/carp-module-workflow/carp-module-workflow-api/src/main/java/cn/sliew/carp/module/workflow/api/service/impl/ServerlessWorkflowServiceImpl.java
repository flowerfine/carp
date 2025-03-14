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

import cn.sliew.carp.framework.dag.x6.dnd.X6DndDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodePortDTO;
import cn.sliew.carp.module.workflow.api.service.ServerlessWorkflowService;
import cn.sliew.carp.module.workflow.api.service.dto.dnd.DndGroupDTO;
import cn.sliew.carp.module.workflow.api.service.dto.dnd.DndNodeDTO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerlessWorkflowServiceImpl implements ServerlessWorkflowService {

    @Override
    public List<DndGroupDTO> getDnds() {
        return Lists.newArrayList(DndGroupDTO.builder()
                .key("serverless-workflow")
                .label("Serverless Workflow")
                .order(1)
                .children(buildDndNodes("serverless-workflow"))
                .build());
    }

    private List<DndNodeDTO> buildDndNodes(String category) {
        return Lists.newArrayList(
                DndNodeDTO.builder()
                        .key("wait")
                        .label("Wait")
                        .order(1)
                        .ports(buildNodePorts())
                        .dndMeta(X6DndDTO.builder()
                                .label("Wait")
                                .name("wait")
                                .type("wait")
                                .icon("/icons/workflow/home.svg")
                                .version(1)
                                .category(category)
                                .author(category)
                                .description("Allows workflows to pause or delay their execution for a specified period of time")
                                .build())
                        .build(),
                DndNodeDTO.builder()
                        .key("http")
                        .label("HTTP")
                        .order(2)
                        .ports(buildNodePorts())
                        .dndMeta(X6DndDTO.builder()
                                .label("HTTP")
                                .name("http")
                                .type("http")
                                .icon("/icons/workflow/http.svg")
                                .version(1)
                                .category(category)
                                .author(category)
                                .description("Defines the HTTP call to perform")
                                .build())
                        .build()
        );
    }

    private List<X6NodePortDTO> buildNodePorts() {
        return Lists.newArrayList(
                X6NodePortDTO.builder()
                        .group("in")
                        .build(),
                X6NodePortDTO.builder()
                        .group("out")
                        .build()
        );
    }


}
