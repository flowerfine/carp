package cn.sliew.carp.module.orca.spinnaker.orca.core.listeners;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

public interface Persister {
    void save(StageExecution stage);

    boolean isCanceled(ExecutionType type, Long executionId);
}
