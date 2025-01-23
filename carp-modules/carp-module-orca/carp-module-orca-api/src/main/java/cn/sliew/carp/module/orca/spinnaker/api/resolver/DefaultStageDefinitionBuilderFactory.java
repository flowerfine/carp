package cn.sliew.carp.module.orca.spinnaker.api.resolver;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import javax.annotation.Nonnull;

public class DefaultStageDefinitionBuilderFactory implements StageDefinitionBuilderFactory {

    private final StageResolver stageResolver;

    public DefaultStageDefinitionBuilderFactory(StageResolver stageResolver) {
        this.stageResolver = stageResolver;
    }

    @Override
    public @Nonnull StageDefinitionBuilder builderFor(@Nonnull StageExecution stage) {
        return stageResolver.getStageDefinitionBuilder(
                stage.getType(), (String) stage.getContext().get("alias"));
    }
}
