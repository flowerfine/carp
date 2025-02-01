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
package cn.sliew.carp.module.orca.core.persistence.sql;

import cn.sliew.carp.framework.dag.service.CarpDagOrcaPipelineService;
import cn.sliew.carp.framework.dag.service.dto.orca.CarpDagOrcaPipelineDTO;
import cn.sliew.carp.framework.dag.service.dto.orca.CarpDagOrcaPipelineStageDTO;
import cn.sliew.carp.framework.dag.service.param.orca.*;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionNotFoundException;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.milky.common.util.JacksonUtil;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SqlExecutionRepository implements ExecutionRepository {

    private CarpDagOrcaPipelineService carpDagOrcaPipelineService;

    public SqlExecutionRepository(CarpDagOrcaPipelineService carpDagOrcaPipelineService) {
        this.carpDagOrcaPipelineService = carpDagOrcaPipelineService;
    }

    @Override
    public PipelineExecution retrieve(ExecutionType type, Long id) throws ExecutionNotFoundException {
        Optional<CarpDagOrcaPipelineDTO> optional = carpDagOrcaPipelineService.get(id);
        CarpDagOrcaPipelineDTO carpDagOrcaPipelineDTO = optional.orElseThrow();
        return convert(carpDagOrcaPipelineDTO);
    }

    @Override
    public Observable<PipelineExecution> retrieve(ExecutionType type) {
        List<CarpDagOrcaPipelineDTO> list = carpDagOrcaPipelineService.listAll(new CarpDagOrcaPipelinePageParam());
        List<PipelineExecution> pipelines = list.stream().map(this::convert).collect(Collectors.toList());
        return Observable.fromIterable(pipelines);
    }

    private PipelineExecution convert(CarpDagOrcaPipelineDTO dto) {
        PipelineExecution execution = JacksonUtil.toObject(dto.getBody(), PipelineExecution.class);

        List<CarpDagOrcaPipelineStageDTO> stageDTOS = carpDagOrcaPipelineService.getStages(dto.getId());
        if (CollectionUtils.isEmpty(stageDTOS) == false) {
            List<StageExecution> stages = stageDTOS.stream().map(item -> {
                        StageExecution stageExecution = JacksonUtil.toObject(item.getBody(), StageExecution.class);
                        ((StageExecutionImpl) stageExecution).setPipelineExecution(execution);
                        return stageExecution;
                    })
                    .collect(Collectors.toList());
            ((PipelineExecutionImpl) execution).setStages(stages);
        } else {
            ((PipelineExecutionImpl) execution).setStages(Collections.emptyList());
        }
        return execution;
    }

    @Override
    public Long store(PipelineExecution execution) {
        // upsert PipelineExecution -> CarpDagOrcaPipelineDTO
        Optional<CarpDagOrcaPipelineDTO> optional = carpDagOrcaPipelineService.get(execution.getId());
        if (optional.isPresent()) {
            CarpDagOrcaPipelineUpdateParam updateParam = CarpDagOrcaPipelineUpdateParam.builder()
                    .id(execution.getId())
                    .uuid(execution.getUuid())
                    .namespace(execution.getNamespace())
                    .type(execution.getType().name())
                    .name(execution.getName())
                    .status(execution.getStatus().name())
                    .buildTime(execution.getBuildTime().toEpochMilli())
                    .canceled(execution.isCanceled())
                    .body(JacksonUtil.toJsonNode(execution))
                    .remark(execution.getRemark())
                    .build();
            Optional.ofNullable(execution.getStartTime()).ifPresent(startTime -> updateParam.setStartTime(startTime.toEpochMilli()));
            Optional.ofNullable(execution.getEndTime()).ifPresent(endTime -> updateParam.setEndTime(endTime.toEpochMilli()));

            carpDagOrcaPipelineService.update(updateParam);
            if (CollectionUtils.isEmpty(execution.getStages()) == false) {
                execution.getStages().forEach(stage -> {
                    storeStage(stage);
                });
            }
            return execution.getId();
        } else {
            CarpDagOrcaPipelineAddParam addParam = CarpDagOrcaPipelineAddParam.builder()
                    .id(execution.getId())
                    .uuid(execution.getUuid())
                    .namespace(execution.getNamespace())
                    .type(execution.getType().name())
                    .name(execution.getName())
                    .body(JacksonUtil.toJsonNode(execution))
                    .remark(execution.getRemark())
                    .build();
            Long id = carpDagOrcaPipelineService.add(addParam);
            if (CollectionUtils.isEmpty(execution.getStages()) == false) {
                execution.getStages().forEach(stage -> {
                    addStage(stage);
                });
            }
            return id;
        }
    }

    @Override
    public Long storeStage(StageExecution stage) {
        Optional<CarpDagOrcaPipelineStageDTO> optional = carpDagOrcaPipelineService.getStage(stage.getId());
        if (optional.isPresent()) {
            // update
            CarpDagOrcaPipelineStageUpdateParam param = CarpDagOrcaPipelineStageUpdateParam.builder()
                    .id(stage.getId())
                    .uuid(stage.getUuid())
                    .pipelineId(stage.getPipelineExecution().getId())
                    .status(stage.getStatus().name())
                    .body(JacksonUtil.toJsonNode(stage))
                    .build();
            carpDagOrcaPipelineService.updateStage(param);
            return stage.getId();
        } else {
            // insert
            return addStage(stage);
        }
    }

    @Override
    public Long addStage(StageExecution stage) {
        CarpDagOrcaPipelineStageAddParam addParam = CarpDagOrcaPipelineStageAddParam.builder()
                .id(stage.getId())
                .uuid(stage.getUuid())
                .pipelineId(stage.getPipelineExecution().getId())
                .status(stage.getStatus().name())
                .body(JacksonUtil.toJsonNode(stage))
                .build();
        return carpDagOrcaPipelineService.addStage(addParam);
    }

    @Override
    public void removeStage(PipelineExecution execution, Long stageId) {
        carpDagOrcaPipelineService.deleteStage(stageId);
        execution.getStages().removeIf(stage -> Objects.equals(stage.getId(), stageId));
        store(execution);
    }

    @Override
    public void updateStageContext(StageExecution stage) {
        storeStage(stage);
    }

    @Override
    public boolean isCanceled(ExecutionType type, Long id) {
        Optional<CarpDagOrcaPipelineDTO> optional = carpDagOrcaPipelineService.get(id);
        return optional.orElseThrow().getCanceled();
    }

    @Override
    public void cancel(ExecutionType type, Long id) {

    }

    @Override
    public void cancel(ExecutionType type, Long id, String user, String reason) {

    }

    @Override
    public void pause(ExecutionType type, Long id, String user) {

    }

    @Override
    public void resume(ExecutionType type, Long id, String user) {

    }

    @Override
    public void resume(ExecutionType type, Long id, String user, boolean ignoreCurrentStatus) {

    }

    @Override
    public void updateStatus(ExecutionType type, Long id, ExecutionStatus status) {
        PipelineExecutionImpl execution = (PipelineExecutionImpl) retrieve(type, id);
        execution.setStatus(status);
        store(execution);
    }

    @Override
    public void delete(ExecutionType type, Long id) {
        carpDagOrcaPipelineService.delete(id);
    }

    @Override
    public void delete(ExecutionType type, List<Long> ids) {
        carpDagOrcaPipelineService.deleteBatch(ids);
    }

    @Override
    public boolean hasExecution(ExecutionType type, Long id) {
        return false;
    }

    @Override
    public List<Long> retrieveAllExecutionIds(ExecutionType type) {
        return null;
    }
}
