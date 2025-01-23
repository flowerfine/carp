package cn.sliew.carp.module.orca.spinnaker.api.model.stage;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.TaskNode;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.WaitTask;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;

@Component
public class WaitStage implements StageDefinitionBuilder {
    public static String STAGE_TYPE = "wait";

    @Override
    public String getType() {
        return STAGE_TYPE;
    }

    @Override
    public void taskGraph(@Nonnull StageExecution stage, @Nonnull TaskNode.Builder builder) {
        builder.withTask("wait", WaitTask.class);
    }

    public static final class WaitStageContext {
        private final Long waitTime;
        private final boolean skipRemainingWait;

        @JsonCreator
        public WaitStageContext(
                @JsonProperty("waitTime") @Nullable Long waitTime,
                @JsonProperty("skipRemainingWait") @Nullable Boolean skipRemainingWait) {
            this.waitTime = waitTime;
            this.skipRemainingWait = skipRemainingWait == null ? false : skipRemainingWait;
        }

        public WaitStageContext(@Nonnull Long waitTime) {
            this(waitTime, false);
        }

        @JsonIgnore
        public Duration getWaitDuration() {
            return waitTime == null ? Duration.ZERO : Duration.ofSeconds(waitTime);
        }

        public boolean isSkipRemainingWait() {
            return skipRemainingWait;
        }
    }
}
