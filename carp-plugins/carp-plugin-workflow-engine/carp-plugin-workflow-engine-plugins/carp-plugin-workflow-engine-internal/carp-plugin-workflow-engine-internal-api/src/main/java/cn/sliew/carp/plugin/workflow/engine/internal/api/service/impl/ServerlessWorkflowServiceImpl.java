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
package cn.sliew.carp.plugin.workflow.engine.internal.api.service.impl;

import cn.sliew.carp.framework.dag.x6.dnd.X6GraphDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodeMetaDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodePortDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.api.service.ServerlessWorkflowService;
import cn.sliew.carp.plugin.workflow.engine.internal.api.service.dto.dnd.DndGroupDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.api.service.dto.dnd.DndNodeDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.api.service.param.ServerlessWorkflowExecuteParam;
import cn.sliew.milky.common.exception.Rethrower;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import io.serverlessworkflow.api.WorkflowFormat;
import io.serverlessworkflow.api.WorkflowReader;
import io.serverlessworkflow.impl.WorkflowApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
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
                        .shape("serverless-workflow-node") // 和前端深度绑定
                        .ports(buildNodePorts("wait"))
                        .dndMeta(X6NodeMetaDTO.builder()
                                .label("Wait")
                                .name("wait")
                                .type("wait")
                                .icon("https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*RXnuTpQ22xkAAAAAAAAAAAAADtOHAQ/original")
//                                .icon("/icons/workflow/home.svg")
                                .version(1)
                                .category(category)
                                .author(category)
                                .description("Allows workflows to pause or delay their execution for a specified period of time")
                                .document("https://serverlessworkflow.io/")
                                .build())
                        .build(),
                DndNodeDTO.builder()
                        .key("http")
                        .label("HTTP")
                        .order(2)
                        .shape("serverless-workflow-node") // 和前端深度绑定
                        .ports(buildNodePorts("http"))
                        .dndMeta(X6NodeMetaDTO.builder()
                                .label("HTTP")
                                .name("http")
                                .type("http")
                                .icon("https://mdn.alipayobjects.com/huamei_f4t1bn/afts/img/A*zUgORbGg1HIAAAAAAAAAAAAADtOHAQ/original")
//                                .icon("/icons/workflow/http.svg")
                                .version(1)
                                .category(category)
                                .author(category)
                                .description("Defines the HTTP call to perform")
                                .document("https://serverlessworkflow.io/")
                                .build())
                        .build()
        );
    }

    private List<X6NodePortDTO> buildNodePorts(String key) {
        return Lists.newArrayList(
                X6NodePortDTO.builder()
                        .id(key + "_in")
                        .group("in")
                        .build(),
                X6NodePortDTO.builder()
                        .id(key + "_in")
                        .group("out")
                        .build()
        );
    }

    @Override
    public String convertDagToWorkflow(X6GraphDTO graphDTO) {
        // 对节点进行拓扑排序，然后顺序执行
        ObjectNode objectNode = JacksonUtil.createObjectNode();
        objectNode.putPOJO("document", buildDocumentNode());
        objectNode.putPOJO("do", buildDoNode());
        return objectNode.toPrettyString();
    }

    private ObjectNode buildDocumentNode() {
        ObjectNode objectNode = JacksonUtil.createObjectNode();
        objectNode.putPOJO("dsl", "1.0.0-alpha5");
        objectNode.putPOJO("namespace", "example");
        objectNode.putPOJO("name", "call-http-shorthand-endpoint");
        objectNode.putPOJO("version", "0.1.0");
        return objectNode;
    }

    private ArrayNode buildDoNode() {
        ArrayNode arrayNode = JacksonUtil.createArrayNode();
        arrayNode.add(buildHttpNode("getPet"));
        return arrayNode;
    }

    private ObjectNode buildHttpNode(String name) {
        ObjectNode node = JacksonUtil.createObjectNode();

        ObjectNode httpNode = JacksonUtil.createObjectNode();
        httpNode.putPOJO("call", "http");

        ObjectNode withNode = JacksonUtil.createObjectNode();
        withNode.putPOJO("method", "get");
        withNode.putPOJO("endpoint", "https://petstore.swagger.io/v2/pet/{petId}");
        httpNode.putPOJO("with", withNode);

        node.putPOJO("name", httpNode);
        return node;
    }

    @Override
    public JsonNode execute(ServerlessWorkflowExecuteParam param) {
        String workflow = convertDagToWorkflow(param.getGraph());
        try (WorkflowApplication appl = WorkflowApplication.builder().build()) {
            return appl.workflowDefinition(WorkflowReader.readWorkflowFromString(workflow, WorkflowFormat.JSON))
                    .instance(param.getParam())
                    .start().get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            Rethrower.throwAs(e);
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage(), e);
            Rethrower.throwAs(e);
        }
        return null;
    }
}
