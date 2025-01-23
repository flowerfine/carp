package cn.sliew.carp.module.orca.spinnaker.api.resolver;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface StageDefinitionBuilderFactory {

    @Nonnull
    StageDefinitionBuilder builderFor(@Nonnull StageExecution stage);

}
