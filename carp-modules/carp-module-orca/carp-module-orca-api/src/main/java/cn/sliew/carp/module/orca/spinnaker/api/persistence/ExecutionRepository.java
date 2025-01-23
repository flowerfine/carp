package cn.sliew.carp.module.orca.spinnaker.api.persistence;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public interface ExecutionRepository {

    PipelineExecution retrieve(ExecutionType type, Long id) throws ExecutionNotFoundException;

    Observable<PipelineExecution> retrieve(ExecutionType type);

    void store(PipelineExecution execution);

    void storeStage(StageExecution stage);

    void addStage(StageExecution stage);

    void removeStage(PipelineExecution execution, Long stageId);

    default void updateStageContext(StageExecution stage) {
        storeStage(stage);
    }

    boolean isCanceled(ExecutionType type, Long id);

    default void cancel(ExecutionType type, Long id) {
        cancel(type, id, null, null);
    }

    void cancel(ExecutionType type, Long id, String user, String reason);

    void pause(ExecutionType type, Long id, String user);

    void resume(ExecutionType type, Long id, String user);

    void resume(ExecutionType type, Long id, String user, boolean ignoreCurrentStatus);

    default void updateStatus(PipelineExecution execution) {
        updateStatus(execution.getType(), execution.getId(), execution.getStatus());
    }

    void updateStatus(ExecutionType type, Long id, ExecutionStatus status);

    void delete(ExecutionType type, Long id);

    default void delete(ExecutionType type, List<Long> ids) {
        ids.forEach(id -> delete(type, id));
    }

    boolean hasExecution(ExecutionType type, Long id);

    List<Long> retrieveAllExecutionIds(ExecutionType type);

    // defaulting to a no-op because in normal cases, this is a no-op for execution repositories
    // execution repositories that support foreign peers can override this to support restarting
    // foreign executions
    default void restartStage(Long executionId, Long stageId) {
    }

}
