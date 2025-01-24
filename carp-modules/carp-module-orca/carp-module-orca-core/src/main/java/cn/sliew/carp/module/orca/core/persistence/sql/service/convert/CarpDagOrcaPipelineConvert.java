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
package cn.sliew.carp.module.orca.core.persistence.sql.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineStageDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarpDagOrcaPipelineConvert extends BaseConvert<DagInstanceComplexDTO, CarpDagOrcaPipelineDTO> {
    CarpDagOrcaPipelineConvert INSTANCE = Mappers.getMapper(CarpDagOrcaPipelineConvert.class);

    @Override
    default DagInstanceComplexDTO toDo(CarpDagOrcaPipelineDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default CarpDagOrcaPipelineDTO toDto(DagInstanceComplexDTO entity) {
        CarpDagOrcaPipelineDTO dto = new CarpDagOrcaPipelineDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getStartTime() != null) {
            dto.setStartTime(entity.getStartTime().toInstant());
        }
        if (entity.getEndTime() != null) {
            dto.setEndTime(entity.getEndTime().toInstant());
        }
        if (entity.getBody() != null) {
            dto.setBody(JacksonUtil.toObject(entity.getBody(), CarpDagOrcaPipelineDTO.CarpDagOrcaPipelineBody.class));
        }
        DAG<CarpDagOrcaPipelineStageDTO> dag = new DAG<>();
        List<DagStepDTO> steps = entity.getSteps();
        List<DagLinkDTO> links = entity.getLinks();
        if (CollectionUtils.isEmpty(steps) == false) {
            Map<String, CarpDagOrcaPipelineStageDTO> stepMap = new HashMap<>();
            for (DagStepDTO step : steps) {
                CarpDagOrcaPipelineStageDTO stageDTO = CarpDagOrcaPipelineStageConvert.INSTANCE.toDto(step);
                stepMap.put(step.getDagConfigStep().getStepId(), stageDTO);
                dag.addNode(stageDTO);
            }
            links.forEach(link -> dag.addEdge(stepMap.get(link.getDagConfigLink().getFromStepId()), stepMap.get(link.getDagConfigLink().getToStepId())));
        }
        dto.setStages(dag);
        return dto;
    }

}
