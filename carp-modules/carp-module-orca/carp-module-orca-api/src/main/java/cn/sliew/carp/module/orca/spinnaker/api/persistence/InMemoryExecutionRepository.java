package cn.sliew.carp.module.orca.spinnaker.api.persistence;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import io.reactivex.rxjava3.core.Observable;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryExecutionRepository implements ExecutionRepository {

    private static final AtomicLong COUNTER = new AtomicLong(0L);

    private final Map<Long, PipelineExecution> pipelines = new ConcurrentHashMap<>();
    private final Map<Long, PipelineExecution> orchestrations = new ConcurrentHashMap<>();
    private final Clock clock = Clock.systemUTC();

    @Override
    public PipelineExecution retrieve(ExecutionType type, Long id) throws ExecutionNotFoundException {
        return Optional.ofNullable(storageFor(type).get(id))
                .orElseThrow(() -> new ExecutionNotFoundException(
                        String.format("No %s found for %s", type, id)
                ));
    }

    @Override
    public Observable<PipelineExecution> retrieve(ExecutionType type) {
        return Observable.fromIterable(storageFor(type).values());
    }

    @Override
    public void store(PipelineExecution execution) {
        Map<Long, PipelineExecution> map = storageFor(execution.getType());
        if (Objects.isNull(execution.getId())) {
            ((PipelineExecutionImpl) execution).setId(COUNTER.incrementAndGet());
        }
        if (CollectionUtils.isNotEmpty(execution.getStages())) {
            execution.getStages().forEach(stage -> {
                ((StageExecutionImpl) stage).setId(COUNTER.incrementAndGet());
            });
        }
        map.put(execution.getId(), execution);
    }

    @Override
    public void storeStage(StageExecution stage) {

    }

    @Override
    public void addStage(StageExecution stage) {

    }

    @Override
    public void removeStage(PipelineExecution execution, Long stageId) {
        execution.getStages().removeIf(stage -> Objects.equals(stage.getId(), stageId));
        store(execution);
    }

    @Override
    public boolean isCanceled(ExecutionType type, Long id) {
        return retrieve(type, id).isCanceled();
    }

    @Override
    public void cancel(ExecutionType type, Long id, String user, String reason) {
        PipelineExecutionImpl execution = (PipelineExecutionImpl) retrieve(type, id);
        execution.setStatus(ExecutionStatus.CANCELED);
        if (user != null) {
            execution.setCanceledBy(user);
        }
        if (reason != null && !reason.isEmpty()) {
            execution.setCancellationReason(reason);
        }
        store(execution);
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
        PipelineExecutionImpl pipelineExecution = (PipelineExecutionImpl) retrieve(type, id);
        pipelineExecution.updateStatus(status);
    }

    @Override
    public void delete(ExecutionType type, Long id) {
        storageFor(type).remove(id);
    }

    @Override
    public boolean hasExecution(ExecutionType type, Long id) {
        return storageFor(type).containsKey(id);
    }

    @Override
    public List<Long> retrieveAllExecutionIds(ExecutionType type) {
        return new ArrayList<>(storageFor(type).keySet());
    }

    private Map<Long, PipelineExecution> storageFor(ExecutionType executionType) {
        switch (executionType) {
            case PIPELINE:
                return pipelines;
            case ORCHESTRATION:
                return orchestrations;
            default:
                throw new IllegalArgumentException("Unknown execution type " + executionType);
        }
    }

    private Map<Long, PipelineExecution> storageFor(String executionId) {
        if (pipelines.containsKey(executionId)) {
            return pipelines;
        }
        if (orchestrations.containsKey(executionId)) {
            return orchestrations;
        }
        return null;
    }
}
