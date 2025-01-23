package cn.sliew.carp.module.orca.spinnaker.api.model.stage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * A stage that is capable of being cancelled.
 */
public interface CancellableStage {

    Result cancel(StageExecution stage);

    @Getter
    @AllArgsConstructor
    class Result {
        private final String stageId;
        private final Map<String, Object> details;
    }
}
