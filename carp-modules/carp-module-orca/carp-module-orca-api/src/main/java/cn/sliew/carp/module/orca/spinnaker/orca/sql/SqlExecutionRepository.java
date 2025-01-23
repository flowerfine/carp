package cn.sliew.carp.module.orca.spinnaker.orca.sql;

import cn.sliew.carp.framework.dag.service.CarpDagOrcaPipelineService;
import cn.sliew.carp.framework.dag.service.dto.orca.CarpDagOrcaPipelineDTO;
import cn.sliew.carp.framework.dag.service.param.orca.CarpDagOrcaPipelineAddParam;
import cn.sliew.carp.framework.dag.service.param.orca.CarpDagOrcaPipelineStageAddParam;
import cn.sliew.carp.framework.dag.service.param.orca.CarpDagOrcaPipelineStageUpdateParam;
import cn.sliew.carp.framework.dag.service.param.orca.CarpDagOrcaPipelineUpdateParam;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionNotFoundException;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlExecutionRepository implements ExecutionRepository {

    private CarpDagOrcaPipelineService carpDagOrcaPipelineService;

    public SqlExecutionRepository(CarpDagOrcaPipelineService carpDagOrcaPipelineService) {
        this.carpDagOrcaPipelineService = carpDagOrcaPipelineService;
    }

    @Override
    public PipelineExecution retrieve(ExecutionType type, Long id) throws ExecutionNotFoundException {
        return null;
    }

    @Override
    public Observable<PipelineExecution> retrieve(ExecutionType type) {
        return null;
    }

    @Override
    public void store(PipelineExecution execution) {
        // upsert PipelineExecution -> CarpDagOrcaPipelineDTO
        if (Objects.nonNull(execution.getId())) {
            CarpDagOrcaPipelineUpdateParam updateParam = new CarpDagOrcaPipelineUpdateParam();
            updateParam.setId(execution.getId());
            updateParam.setNamespace(execution.getNamespace());
            updateParam.setType(execution.getType().name());
            updateParam.setConfigId(execution.getPipelineConfigId());
            updateParam.setName(execution.getName());
            updateParam.setStatus(execution.getStatus().name());
            updateParam.setBuildTime(execution.getBuildTime().toEpochMilli());
            Optional.ofNullable(execution.getStartTime()).ifPresent(startTime -> updateParam.setStartTime(startTime.toEpochMilli()));
            Optional.ofNullable(execution.getEndTime()).ifPresent(endTime -> updateParam.setEndTime(endTime.toEpochMilli()));
            updateParam.setCanceled(execution.isCanceled());
            updateParam.setBody(JacksonUtil.toJsonNode(execution));
            updateParam.setRemark(execution.getRemark());
            carpDagOrcaPipelineService.update(updateParam);
        } else {
            CarpDagOrcaPipelineAddParam addParam = new CarpDagOrcaPipelineAddParam();
            addParam.setNamespace(execution.getNamespace());
            addParam.setType(execution.getType().name());
            addParam.setConfigId(execution.getPipelineConfigId());
            addParam.setName(execution.getName());
            addParam.setBody(JacksonUtil.toJsonNode(execution));
            addParam.setRemark(execution.getRemark());
            carpDagOrcaPipelineService.add(addParam);
        }
    }

    @Override
    public void storeStage(StageExecution stage) {
        try {
            carpDagOrcaPipelineService.getStage(stage.getId());
            // update
            CarpDagOrcaPipelineStageUpdateParam param = new CarpDagOrcaPipelineStageUpdateParam();
            param.setId(stage.getId());
            param.setUuid(stage.getUuid());
            param.setPipelineId(stage.getPipelineExecution().getId());
            param.setStatus(stage.getStatus().name());
            param.setBody(JacksonUtil.toJsonNode(stage));
            carpDagOrcaPipelineService.updateStage(param);
        } catch (NullPointerException ignored) {
            // insert
            addStage(stage);
        }
    }

    @Override
    public void addStage(StageExecution stage) {
        CarpDagOrcaPipelineStageAddParam addParam = new CarpDagOrcaPipelineStageAddParam();
        addParam.setPipelineId(stage.getPipelineExecution().getId());
        addParam.setStatus(stage.getStatus().name());
        addParam.setBody(JacksonUtil.toJsonNode(stage));
        carpDagOrcaPipelineService.addStage(addParam);
    }

    @Override
    public void removeStage(PipelineExecution execution, Long stageId) {
        carpDagOrcaPipelineService.deleteStage(stageId);
    }

    @Override
    public void updateStageContext(StageExecution stage) {
        storeStage(stage);
    }

    @Override
    public boolean isCanceled(ExecutionType type, Long id) {
        CarpDagOrcaPipelineDTO carpDagOrcaPipelineDTO = carpDagOrcaPipelineService.get(id);
        return carpDagOrcaPipelineDTO.getCanceled();
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
