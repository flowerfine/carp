package cn.sliew.carp.module.orca.config;

import cn.sliew.carp.module.orca.spinnaker.api.executions.ExecutionRunner;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.Task;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.InMemoryExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.*;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionFunctionProvider;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.config.ExpressionProperties;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.QueueExecutionRunner;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.pending.InMemoryPendingExecutionService;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.pending.PendingExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.List;

@Slf4j
@Configuration
public class SpinnakerOrcaConfig {

    public static final String IN_MEMORY_EXECUTION_REPOSITORY = "in-memory-execution-repository";
    public static final String QUEUE_EVENT_PUBLISHER = "queueEventPublisher";
    public static final String CANCELLABLE_STAGE_EXECUTOR = "cancellable-stage-executor";

    @Bean(IN_MEMORY_EXECUTION_REPOSITORY)
    public ExecutionRepository inMemoryExecutionRepository() {
        return new InMemoryExecutionRepository();
    }

    @Bean
    public DefaultStageResolver defaultStageResolver(
            ObjectProvider<Collection<StageDefinitionBuilder>> stageDefinitionBuilders) {
        return new DefaultStageResolver(stageDefinitionBuilders);
    }

    @Bean
    public TaskResolver taskResolver(ObjectProvider<Collection<Task>> tasks) {
        return new TaskResolver(tasks, true);
    }


    @Bean
    public TaskImplementationResolver defaultTaskImplementationResolver() {
        return new NoOpTaskImplementationResolver();
    }

    @Bean
    public StageDefinitionBuilderFactory stageDefinitionBuilderFactory(StageResolver stageResolver) {
        return new DefaultStageDefinitionBuilderFactory(stageResolver);
    }

    @Bean
    public ExecutionRunner queueExecutionRunner(Queue queue) {
        return new QueueExecutionRunner(queue);
    }

    @Bean
    public PendingExecutionService pendingExecutionService() {
        return new InMemoryPendingExecutionService();
    }

    @Bean
    public ContextParameterProcessor contextParameterProcessor(
            List<ExpressionFunctionProvider> expressionFunctionProviders,
            ExpressionProperties expressionProperties) {
        return new ContextParameterProcessor(
                expressionFunctionProviders, expressionProperties);
    }

    @Bean(CANCELLABLE_STAGE_EXECUTOR)
    public ThreadPoolTaskExecutor cancellableStageExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setThreadNamePrefix("cancel-");
        threadPool.setCorePoolSize(5);
        threadPool.setMaxPoolSize(10);
        threadPool.setQueueCapacity(20);
        return threadPool;
    }
}
