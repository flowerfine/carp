package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;

public interface StageBuilderAware {

    StageDefinitionBuilderFactory getStageDefinitionBuilderFactory();

    default StageDefinitionBuilder builder(StageExecution stage) {
        return getStageDefinitionBuilderFactory().builderFor(stage);
    }

}
