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
import cn.sliew.carp.framework.dag.service.dto.DagConfigComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineConfigDTO;
import cn.sliew.carp.module.orca.core.persistence.sql.service.dto.CarpDagOrcaPipelineStageConfigDTO;
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
public interface CarpDagOrcaPipelineConfigConvert extends BaseConvert<DagConfigComplexDTO, CarpDagOrcaPipelineConfigDTO> {
    CarpDagOrcaPipelineConfigConvert INSTANCE = Mappers.getMapper(CarpDagOrcaPipelineConfigConvert.class);

    @Override
    default DagConfigComplexDTO toDo(CarpDagOrcaPipelineConfigDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default CarpDagOrcaPipelineConfigDTO toDto(DagConfigComplexDTO entity) {
        CarpDagOrcaPipelineConfigDTO dto = new CarpDagOrcaPipelineConfigDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getDagMeta() != null) {
            dto.setMeta(
                    JacksonUtil.toObject(entity.getDagMeta(),
                            CarpDagOrcaPipelineConfigDTO.CarpDagOrcaPipelineConfigMeta.class));
        }
        if (entity.getDagAttrs() != null) {
            dto.setAttrs(
                    JacksonUtil.toObject(entity.getDagMeta(),
                            CarpDagOrcaPipelineConfigDTO.CarpDagOrcaPipelineConfigAttrs.class));
        }

        DAG<CarpDagOrcaPipelineStageConfigDTO> dag = new DAG<>();
        List<DagConfigStepDTO> steps = entity.getSteps();
        List<DagConfigLinkDTO> links = entity.getLinks();
        if (CollectionUtils.isEmpty(steps) == false) {
            Map<String, CarpDagOrcaPipelineStageConfigDTO> stepMap = new HashMap<>();
            for (DagConfigStepDTO step : steps) {
                CarpDagOrcaPipelineStageConfigDTO stageConfigDTO = CarpDagOrcaPipelineStageConfigConvert.INSTANCE.toDto(step);
                stepMap.put(stageConfigDTO.getUuid(), stageConfigDTO);
                dag.addNode(stageConfigDTO);
            }
            links.forEach(link -> dag.addEdge(stepMap.get(link.getFromStepId()), stepMap.get(link.getToStepId())));
        }
        dto.setStages(dag);
        return dto;
    }

}
