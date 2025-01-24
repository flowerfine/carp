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
package cn.sliew.carp.module.orca.core.persistence.sql.service.impl;

import cn.sliew.carp.framework.dag.service.DagConfigComplexService;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.CarpOrcaPipelineService;
import cn.sliew.carp.module.orca.core.persistence.sql.service.convert.CarpDagOrcaPipelineConfigConvert;
import cn.sliew.carp.module.orca.core.persistence.sql.service.convert.CarpDagOrcaPipelineConvert;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineConfigDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineStageDTO;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import cn.sliew.milky.common.util.JacksonUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpOrcaPipelineServiceImpl implements CarpOrcaPipelineService {

    @Autowired
    private DagConfigComplexService dagConfigComplexService;
    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;

    @Override
    public PipelineExecution get(ExecutionType type, Long id) {
        DagInstanceComplexDTO dagInstanceComplexDTO = dagInstanceComplexService.selectOne(id);
        CarpDagOrcaPipelineConfigDTO pipelineConfigDTO = CarpDagOrcaPipelineConfigConvert.INSTANCE.toDto(dagInstanceComplexDTO.getDagConfig());
        CarpDagOrcaPipelineDTO pipelineDTO = CarpDagOrcaPipelineConvert.INSTANCE.toDto(dagInstanceComplexDTO);
        return convert(pipelineConfigDTO, pipelineDTO);
    }

    private PipelineExecutionImpl convert(CarpDagOrcaPipelineConfigDTO pipelineConfigDTO, CarpDagOrcaPipelineDTO pipelineDTO) {
        PipelineExecutionImpl execution = new PipelineExecutionImpl();
        execution.setNamespace(pipelineConfigDTO.getMeta().getNamespace());
        execution.setType(EnumUtils.getEnum(ExecutionType.class, pipelineConfigDTO.getMeta().getType()));
        execution.setId(pipelineDTO.getId());
        execution.setUuid(pipelineDTO.getUuid());
        execution.setName(pipelineConfigDTO.getName());
        execution.setPipelineConfigId(pipelineConfigDTO.getId());
        execution.setLimitConcurrent(pipelineConfigDTO.getAttrs().isLimitConcurrent());
        execution.setMaxConcurrentExecutions(pipelineConfigDTO.getAttrs().getMaxConcurrentExecutions());
        execution.setKeepWaitingPipelines(pipelineConfigDTO.getAttrs().isKeepWaitingPipelines());
        execution.setBuildTime(pipelineDTO.getBody().getBuildTime());
        execution.setStartTime(pipelineDTO.getStartTime());
        execution.setEndTime(pipelineDTO.getEndTime());
        execution.setStartTimeExpiry(pipelineDTO.getBody().getStartTimeExpiry());
        execution.setPaused(JacksonUtil.toObject(pipelineDTO.getBody().getPaused(), PipelineExecutionImpl.PausedDetails.class));
        execution.setCanceled(pipelineDTO.getBody().isCanceled());
        execution.setCanceledBy(pipelineDTO.getBody().getCanceledBy());
        execution.setCancellationReason(pipelineDTO.getBody().getCancellationReason());
        execution.setTrigger(JacksonUtil.toObject(pipelineDTO.getBody().getTrigger(), Trigger.class));
        execution.setNotifications(pipelineConfigDTO.getAttrs().getNotifications());
        execution.setSpelEvaluator(pipelineConfigDTO.getAttrs().getSpelEvaluator());
        execution.setTemplateVariables(pipelineConfigDTO.getAttrs().getTemplateVariables());
        execution.setStatus(EnumUtils.getEnum(ExecutionStatus.class, pipelineDTO.getStatus()));

        List<StageExecution> stages = Lists.newArrayList();
        for (CarpDagOrcaPipelineStageDTO stageDTO : pipelineDTO.getStages().nodes()) {
            StageExecutionImpl stage = new StageExecutionImpl();
            stage.setId(stageDTO.getId());
            stage.setUuid(stageDTO.getUuid());
            stage.setRefId(stageDTO.getUuid());
            stage.setType(null);

            stages.add(stage);
        }
        execution.setStages(stages);

        return execution;
    }
}
