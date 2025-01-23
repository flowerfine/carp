package cn.sliew.carp.module.orca.spinnaker.orca.core.listeners;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import java.util.List;

public class ExecutionCleanupListener implements ExecutionListener {

    @Override
    public void afterExecution(
            Persister persister,
            PipelineExecution execution,
            ExecutionStatus executionStatus,
            boolean wasSuccessful) {
        if (!execution.getStatus().isSuccessful()) {
            // only want to cleanup executions that successfully completed
            return;
        }

        List<StageExecution> stages = execution.getStages();
        stages.forEach(
                it -> {
                    if (it.getContext().containsKey("targetReferences")) {
                        // remove `targetReferences` as it's large and unnecessary after a pipeline has
                        // completed
                        it.getContext().remove("targetReferences");
                        persister.save(it);
                    }
                });
    }
}
