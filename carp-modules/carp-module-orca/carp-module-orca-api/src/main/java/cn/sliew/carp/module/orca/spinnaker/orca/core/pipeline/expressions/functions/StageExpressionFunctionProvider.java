package cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.expressions.functions;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionContext;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionFunctionProvider;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.SpelHelperFunctionException;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class StageExpressionFunctionProvider implements ExpressionFunctionProvider {
    @Nullable
    @Override
    public String getNamespace() {
        return null;
    }

    @NotNull
    @Override
    public Functions getFunctions() {
        FunctionParameter[] stageParameters = {
                new FunctionParameter(PipelineExecution.class, "execution", "The execution for the stage"),
                new FunctionParameter(String.class, "idOrName", "The name or id of the stage to find")
        };

        return new Functions(
                new FunctionDefinition(
                        "currentStage",
                        "Returns the current stage object",
                        new FunctionParameter(
                                PipelineExecution.class,
                                "execution",
                                "The execution containing the currently executing stage")),
                new FunctionDefinition(
                        "currentUser", "Looks up the current authenticated user within the execution context."),
                new FunctionDefinition(
                        "stageByRefId",
                        "Locates and returns a stage with the given refId",
                        new FunctionParameter(
                                PipelineExecution.class,
                                "execution",
                                "The execution containing the currently executing stage"),
                        new FunctionParameter(String.class, "refId", "A valid stage reference identifier")),
                new FunctionDefinition(
                        "stage",
                        "Locates a stage by name",
                        Arrays.asList(stageParameters),
                        new FunctionDocumentation(
                                "The most common use of this function is to check whether a specific stage has succeeded or failed. It can also be used to retrieve any information from the specified stage.",
                                new FunctionUsageExample(
                                        "#stage('bake').hasSucceeded",
                                        "Returns `true` if the stage with the name `bake` has succeeded"),
                                new FunctionUsageExample(
                                        "#stage('bake').hasFailed",
                                        "Returns `true` if the stage with the name `bake` has failed"))),
                new FunctionDefinition(
                        "stageExists",
                        "Checks if the stage with the specified name exists in the current execution",
                        stageParameters),
                new FunctionDefinition(
                        "judgment",
                        "Returns the judgement made by the user in the given manual judgement stage",
                        stageParameters),
                new FunctionDefinition(
                        "judgement",
                        "Returns the judgement made by the user in the given manual judgement stage",
                        stageParameters));
    }

    /**
     * @param execution the current execution
     * @return the currently executing stage
     */
    public static StageExecution currentStage(PipelineExecution execution) {
        ExecutionContext executionContext = ExecutionContext.get();
        if (executionContext == null) {
            throw new SpelHelperFunctionException("An execution context is required for this function");
        }

        Long currentStageId = ExecutionContext.get().getStageId();
        return execution.getStages().stream()
                .filter(s -> Objects.equals(s.getId(), currentStageId))
                .findFirst()
                .orElseThrow(
                        () ->
                                new SpelHelperFunctionException("No stage found with id '" + currentStageId + "'"));
    }

    /**
     * @return the current authenticated user in the Execution or anonymous.
     */
    @SuppressWarnings("unused")
    public static String currentUser() {
        return "anonymous";
    }

    /**
     * Finds a Stage by refId. This function should only be used by programmatic pipeline generators,
     * as refIds are fragile and may change from execution-to-execution.
     *
     * @param execution the current execution
     * @param refId     the stage reference ID
     * @return a stage specified by refId
     */
    public static StageExecution stageByRefId(PipelineExecution execution, String refId) {
        if (refId == null) {
            throw new SpelHelperFunctionException(
                    String.format(
                            "Stage refId must not be null in #stageByRefId in execution %s", execution.getId()));
        }
        return execution.getStages().stream()
                .filter(s -> refId.equals(s.getRefId()))
                .findFirst()
                .orElseThrow(
                        () ->
                                new SpelHelperFunctionException(
                                        String.format(
                                                "Unable to locate [%1$s] using #stageByRefId(%1$s) in execution %2$s",
                                                refId, execution.getId())));
    }

    /**
     * Finds a Stage by id
     *
     * @param execution #root.execution
     * @param id        the name or id of the stage to find
     * @return a stage specified by id
     */
    public static StageExecution stage(PipelineExecution execution, String id) {
        return execution.getStages().stream()
                .filter(i -> id != null && (id.equals(i.getName()) || id.equals(i.getId())))
                .findFirst()
                .orElseThrow(
                        () ->
                                new SpelHelperFunctionException(
                                        String.format(
                                                "Unable to locate [%s] using #stage(%s) in execution %s",
                                                id, id, execution.getId())));
    }

    /**
     * Checks existence of a Stage by id
     *
     * @param execution #root.execution
     * @param id        the name or id of the stage to check existence
     * @return W
     */
    public static boolean stageExists(PipelineExecution execution, String id) {
        return execution.getStages().stream()
                .anyMatch(i -> id != null && (id.equals(i.getName()) || id.equals(i.getId())));
    }

    /**
     * Finds a stage by id and returns the judgment input text
     *
     * @param execution #root.execution
     * @param id        the name of the stage to find
     * @return the judgment input text
     */
    public static String judgment(PipelineExecution execution, String id) {
        StageExecution stageWithJudgmentInput =
                execution.getStages().stream()
                        .filter(isManualStageWithManualInput(id))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new SpelHelperFunctionException(
                                                String.format(
                                                        "Unable to locate manual Judgment stage [%s] using #judgment(%s) in execution %s. "
                                                                + "Stage doesn't exist or doesn't contain judgmentInput in its context ",
                                                        id, id, execution.getId())));

        return (String) stageWithJudgmentInput.getContext().get("judgmentInput");
    }

    /**
     * Alias to judgment
     */
    @SuppressWarnings("unused")
    public static String judgement(PipelineExecution execution, String id) {
        return judgment(execution, id);
    }

    private static Predicate<StageExecution> isManualStageWithManualInput(String id) {
        return i ->
                (id != null && id.equals(i.getName()))
                        && (i.getContext() != null
                        && i.getType().equals("manualJudgment")
                        && i.getContext().get("judgmentInput") != null);
    }
}
