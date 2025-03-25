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
package cn.sliew.carp.module.workflow.simple.api.impl;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.dag.service.DagConfigLinkService;
import cn.sliew.carp.framework.dag.service.DagConfigService;
import cn.sliew.carp.framework.dag.service.DagConfigStepService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.module.workflow.simple.api.WorkflowDefinitionApi;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowDefinitionRequest;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowLinkDefinition;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowStepDefinition;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WorkflowDefinitionApiImpl implements WorkflowDefinitionApi {

    @Autowired
    private DagConfigService dagConfigService;
    @Autowired
    private DagConfigStepService dagConfigStepService;
    @Autowired
    private DagConfigLinkService dagConfigLinkService;

    @Override
    public Long create(WorkflowDefinitionRequest request) {
        // 插入 dag
        DagConfigDTO dagConfigDTO = new DagConfigDTO();
        dagConfigDTO.setNamespace(request.getNamespace());
        dagConfigDTO.setType(request.getType());
        dagConfigDTO.setName(request.getName());
        if (StringUtils.isNotBlank(request.getUuid())) {
            dagConfigDTO.setUuid(request.getUuid());
        } else {
            dagConfigDTO.setUuid(UUIDUtil.randomUUId());
        }
        Long dagConfigId = dagConfigService.add(dagConfigDTO);

        // 插入 step
        if (Objects.nonNull(request.getPreStep())) {
            dagConfigStepService.add(convertStep(dagConfigId, request.getNamespace(), request.getPreStep()));
        }
        if (CollectionUtils.isNotEmpty(request.getStepList())) {
            request.getStepList().forEach(step -> {
                dagConfigStepService.add(convertStep(dagConfigId, request.getNamespace(), step));
            });
        }
        if (Objects.nonNull(request.getPostStep())) {
            dagConfigStepService.add(convertStep(dagConfigId, request.getNamespace(), request.getPostStep()));
        }

        // 插入 link
        if (CollectionUtils.isNotEmpty(request.getLinkList())) {
            request.getLinkList().forEach(link -> {
                dagConfigLinkService.add(convertLink(dagConfigId, request.getNamespace(), link));
            });
        }
        return dagConfigId;
    }

    private DagConfigStepDTO convertStep(Long dagId, String namespace, WorkflowStepDefinition stepDefinition) {
        DagConfigStepDTO step = new DagConfigStepDTO();
        step.setNamespace(namespace);
        step.setDagId(dagId);
        step.setStepId(stepDefinition.getUuid());
        step.setStepName(stepDefinition.getName());

        ObjectNode stepAttrs = JacksonUtil.createObjectNode();
        if (MapUtils.isNotEmpty(stepDefinition.getParams())) {
            stepAttrs.putPOJO("params", stepDefinition.getParams());
        }
        stepAttrs.put("maxRetryTimes", stepDefinition.getMaxRetryTimes());
        stepAttrs.put("retryExpression", stepDefinition.getRetryExpression());
        stepAttrs.put("runTimeout", stepDefinition.getRunTimeout());
        step.setStepAttrs(stepAttrs);

        ObjectNode stepMeta = JacksonUtil.createObjectNode();
        stepMeta.putPOJO("stepType", stepDefinition.getType().getValue());

        step.setStepMeta(stepMeta);
        return step;
    }

    private DagConfigLinkDTO convertLink(Long dagId, String namespace, WorkflowLinkDefinition linkDefinition) {
        DagConfigLinkDTO link = new DagConfigLinkDTO();
        link.setNamespace(namespace);
        link.setDagId(dagId);
        link.setLinkName(linkDefinition.getName());
        if (StringUtils.isNotBlank(linkDefinition.getUuid())) {
            link.setLinkId(linkDefinition.getUuid());
        } else {
            link.setLinkId(UUIDUtil.randomUUId());
        }
        link.setFromStepId(linkDefinition.getFromStepId());
        link.setToStepId(linkDefinition.getToStepId());

        ObjectNode linkAttrs = JacksonUtil.createObjectNode();

        linkAttrs.put("expression", linkDefinition.getExpression());
        link.setLinkAttrs(linkAttrs);

        return link;
    }
}
