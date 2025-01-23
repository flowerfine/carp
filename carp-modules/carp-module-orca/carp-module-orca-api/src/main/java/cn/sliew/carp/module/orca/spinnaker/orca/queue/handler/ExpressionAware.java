package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageContext;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionEvaluationSummary;
import cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions.ExceptionHandler;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.ExpressionAwareStageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.expressions.PipelineExpressionEvaluator;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public interface ExpressionAware {

    default Logger getLog() {
        return LoggerFactory.getLogger(getClass());
    }

    StageDefinitionBuilderFactory getStageDefinitionBuilderFactory();

    ContextParameterProcessor getContextParameterProcessor();

    default StageExecution withMergedContext(StageExecution stage) {
        ExpressionEvaluationSummary summary = new ExpressionEvaluationSummary();
        Map<String, Object> processed = processEntries(stage, summary);

        ((StageExecutionImpl) stage).setContext(new DelegatingMutableMap(processed, stage, this));

        // 清理错误:由于表达式可能被多次评估,可能在执行开始前某些数据不可用导致评估失败。
        // 如果该评估后来成功,确保从上下文中删除过去的错误消息。
        // 否则,在UI中会很混乱,因为值明显被正确评估但错误仍然显示
        if (hasFailedExpressions(stage)) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> failedExpressions = (Map<String, Object>) stage.getContext().get(PipelineExpressionEvaluator.SUMMARY);

                List<String> keysToRemove = new ArrayList<>();
                for (String expressionKey : failedExpressions.keySet()) {
                    if (summary.wasAttempted(expressionKey) && !summary.hasFailed(expressionKey)) {
                        keysToRemove.add(expressionKey);
                    }
                }

                for (String key : keysToRemove) {
                    failedExpressions.remove(key);
                }
            } catch (Exception e) {
                // 尽最大努力清理,如果失败只记录错误并保持上下文不变
                getLog().error("Failed to remove stale expression errors", e);
            }
        }

        return stage;
    }

    /**
     * 包含表达式评估摘要
     */
    default void includeExpressionEvaluationSummary(StageExecution stage) {
        if (hasFailedExpressions(stage)) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, List<Map<String, String>>> expressionEvaluationSummary =
                        (Map<String, List<Map<String, String>>>) stage.getContext().get(PipelineExpressionEvaluator.SUMMARY);

                List<String> evaluationErrors = new ArrayList<>();
                for (List<Map<String, String>> summaryValue : expressionEvaluationSummary.values()) {
                    for (Map<String, String> entry : summaryValue) {
                        evaluationErrors.add(entry.get("description"));
                    }
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> exception = (Map<String, Object>) stage.getContext().get("exception");
                stage.getContext().put("exception",
                        mergedExceptionErrors(exception, evaluationErrors));
            } catch (Exception e) {
                getLog().error("failed to include expression evaluation error in context", e);
            }
        }
    }

    /**
     * 检查阶段是否有失败的表达式
     */
    default boolean hasFailedExpressions(StageExecution stage) {
        return stage.getContext().containsKey(PipelineExpressionEvaluator.SUMMARY) &&
                !((Map) stage.getContext().get(PipelineExpressionEvaluator.SUMMARY)).isEmpty();
    }

    /**
     * 检查阶段是否应该在表达式评估失败时失败
     */
    default boolean shouldFailOnFailedExpressionEvaluation(StageExecution stage) {
        return hasFailedExpressions(stage) &&
                stage.getContext().containsKey("failOnFailedExpressions") &&
                Boolean.TRUE.equals(stage.getContext().get("failOnFailedExpressions"));
    }

    /**
     * 合并异常错误
     */
    default Map<String, Object> mergedExceptionErrors(Map<String, Object> exception, List<String> errors) {
        if (exception == null) {
            return Map.of("details", ExceptionHandler.responseDetails(PipelineExpressionEvaluator.ERROR, errors));
        }

        Map<String, Object> details = (Map<String, Object>) exception.get("details");
        if (details == null) {
            details = new HashMap<>();
            details.put("errors", new ArrayList<String>());
        }

        List<String> mergedErrors = new ArrayList<>();
        mergedErrors.addAll((List<String>) details.get("errors"));
        mergedErrors.addAll(errors);

        return Map.of("details", Map.of("errors", mergedErrors));
    }

    /**
     * 处理条目
     */
    default StageContext processEntries(StageExecution stage, ExpressionEvaluationSummary summary) {
        boolean shouldContinueProcessing = true;

        PipelineExpressionEvaluator.SpelEvaluatorVersion spelVersion = getContextParameterProcessor()
                .getEffectiveSpelVersionToUse(stage.getPipelineExecution().getSpelEvaluator());

        if (PipelineExpressionEvaluator.SpelEvaluatorVersion.V4 == spelVersion) {
            // 让阶段首先处理其表达式(如果需要)
            ExpressionAwareStageDefinitionBuilder stageBuilder =
                    (ExpressionAwareStageDefinitionBuilder) getStageDefinitionBuilderFactory().builderFor(stage);

            if (stageBuilder instanceof ExpressionAwareStageDefinitionBuilder) {
                shouldContinueProcessing = stageBuilder.processExpressions(
                        stage,
                        getContextParameterProcessor(),
                        summary
                );
            }
        }

        if (shouldContinueProcessing) {
            return new StageContext(stage, getContextParameterProcessor().process(
                    stage.getContext(),
                    getContextParameterProcessor().buildExecutionContext(stage),
                    true,
                    summary
            ));
        }

        return new StageContext(stage, stage.getContext());
    }

    /**
     * 委托的可变Map实现
     */
    class DelegatingMutableMap implements Map<String, Object> {
        private final Map<String, Object> delegate;
        private final StageExecution stage;
        private final ExpressionAware expressionAware;

        public DelegatingMutableMap(Map<String, Object> delegate, StageExecution stage, ExpressionAware expressionAware) {
            this.delegate = delegate;
            this.stage = stage;
            this.expressionAware = expressionAware;
        }

        @Override
        public Object get(Object key) {
            if (stage.getPipelineExecution().getType() == ExecutionType.PIPELINE) {
                if ("trigger".equals(key)) {
                    return stage.getPipelineExecution().getTrigger();
                }
                if ("execution".equals(key)) {
                    return stage.getPipelineExecution();
                }
            }

            Object result = delegate.get(key);
            if (result instanceof String &&
                    ContextParameterProcessor.containsExpression((String) result)) {
                Map<String, Object> augmentedContext =
                        expressionAware.getContextParameterProcessor().buildExecutionContext(stage);
                return expressionAware.getContextParameterProcessor().process(
                        Map.of((String) key, result),
                        augmentedContext,
                        true
                ).get(key);
            }
            return result;
        }

        // 实现 Map 接口的其他方法,委托给 delegate
        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return delegate.containsValue(value);
        }

        @Override
        public Object put(String key, Object value) {
            return delegate.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return delegate.remove(key);
        }

        @Override
        public void putAll(Map<? extends String, ?> m) {
            delegate.putAll(m);
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public Set<String> keySet() {
            return delegate.keySet();
        }

        @Override
        public Collection<Object> values() {
            return delegate.values();
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return delegate.entrySet();
        }
    }
}