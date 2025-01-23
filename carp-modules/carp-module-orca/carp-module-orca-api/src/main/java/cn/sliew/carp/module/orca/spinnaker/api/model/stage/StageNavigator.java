package cn.sliew.carp.module.orca.spinnaker.api.model.stage;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Provides an enhanced version of {@link StageExecution#ancestors()} that returns tuples of the
 * ancestor stages and their {@link StageDefinitionBuilder}s.
 */
@Component
@AllArgsConstructor
public class StageNavigator {

    private StageResolver stageResolver;

    /**
     * As per `Stage.ancestors` except this method returns tuples of the stages and their
     * `StageDefinitionBuilder`.
     */
    public List<Result> ancestors(StageExecution startingStage) {
        return startingStage.ancestors().stream()
                .map(
                        it ->
                                new Result(
                                        it,
                                        stageResolver.getStageDefinitionBuilder(
                                                it.getType(), (String) it.getContext().get("alias"))))
                .collect(toList());
    }

    @Getter
    @AllArgsConstructor
    public static class Result {
        private final StageExecution stage;
        private final StageDefinitionBuilder stageBuilder;
    }
}
