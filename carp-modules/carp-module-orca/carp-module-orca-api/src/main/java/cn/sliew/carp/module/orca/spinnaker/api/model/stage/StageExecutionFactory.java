package cn.sliew.carp.module.orca.spinnaker.api.model.stage;

import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Pulled from StageDefinitionBuilder. No idea what is supposed to replace this method, but it's
 * still used everywhere.
 */
public class StageExecutionFactory {

    @Deprecated
    public static @Nonnull StageExecutionImpl newStage(
            @Nonnull PipelineExecution execution,
            @Nonnull String type,
            @Nullable String name,
            @Nonnull Map<String, Object> context,
            @Nullable StageExecution parent,
            @Nullable SyntheticStageOwner stageOwner) {
        StageExecutionImpl stage = new StageExecutionImpl(execution, type, name, context);
        if (parent != null) {
            stage.setParentStageId(parent.getId());
        }
        stage.setSyntheticStageOwner(stageOwner);
        return stage;
    }
}
