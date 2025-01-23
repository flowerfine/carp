package cn.sliew.carp.module.orca.spinnaker.orca.core.listeners;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultPersister implements Persister {
    private final ExecutionRepository executionRepository;

    @Override
    public void save(StageExecution stage) {
        executionRepository.storeStage(stage);
    }

    @Override
    public boolean isCanceled(ExecutionType type, Long executionId) {
        return executionRepository.isCanceled(type, executionId);
    }
}
