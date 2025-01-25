/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sliew.carp.module.orca.spinnaker.api.executions;

import cn.sliew.carp.framework.exception.HasAdditionalAttributes;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionNotFoundException;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.BeforeInitialExecutionPersist;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Slf4j
@Component
public class ExecutionLauncher {

    private final ObjectMapper objectMapper;
    private final ExecutionRepository executionRepository;
    private final ExecutionRunner executionRunner;
    private final Clock clock;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ExecutionLauncher(
            ObjectMapper objectMapper,
            ExecutionRepository executionRepository,
            ExecutionRunner executionRunner,
            Clock clock,
            ApplicationEventPublisher applicationEventPublisher) {
        this.objectMapper = objectMapper;
        this.executionRepository = executionRepository;
        this.executionRunner = executionRunner;
        this.clock = clock;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public PipelineExecution start(ExecutionType type, Map<String, Object> config) {
        final PipelineExecution execution = parse(type, config);
        final PipelineExecution existingExecution = checkForCorrelatedExecution(execution);
        if (existingExecution != null) {
            return existingExecution;
        }

        persistExecution(execution);

        try {
            start(execution);
        } catch (Throwable t) {
            handleStartupFailure(execution, t);
        }

        return execution;
    }

    /**
     * Log that an execution failed; useful if a pipeline failed validation and we want to persist the
     * failure to the execution history but don't actually want to attempt to run the execution.
     *
     * @param e the exception that was thrown during pipeline validation
     */
    public PipelineExecution fail(ExecutionType type, Map<String, Object> config, Exception e) {
        final PipelineExecution execution = parse(type, config);

        persistExecution(execution);

        handleStartupFailure(execution, e);

        return execution;
    }

    public PipelineExecution start(PipelineExecution execution) {
        executionRunner.start(execution);
        return execution;
    }

    private PipelineExecution checkForCorrelatedExecution(PipelineExecution execution) {
        if (execution.getTrigger().getCorrelationId() == null) {
            return null;
        }

        Trigger trigger = execution.getTrigger();

        try {
//            PipelineExecution o =
//                    executionRepository.retrieveByCorrelationId(
//                            execution.getType(), trigger.getCorrelationId());
//            log.info(
//                    "Found pre-existing {} by correlation id (id: {}, correlationId: {})",
//                    execution.getType(),
//                    o.getId(),
//                    trigger.getCorrelationId());
//            return o;
        } catch (ExecutionNotFoundException e) {
            // Swallow
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void handleStartupFailure(PipelineExecution execution, Throwable failure) {
        final String canceledBy = "system";
        String reason = "Failed on startup: " + failure.getMessage();

        if (failure instanceof HasAdditionalAttributes) {
            HasAdditionalAttributes exceptionWithAttributes = (HasAdditionalAttributes) failure;
            if (exceptionWithAttributes.getAdditionalAttributes().containsKey("errors")) {
                List<Map<String, Object>> errors =
                        ((List<Map<String, Object>>)
                                exceptionWithAttributes.getAdditionalAttributes().get("errors"));
                reason +=
                        errors.stream()
                                .flatMap(
                                        error ->
                                                error.entrySet().stream()
                                                        .filter(entry -> !entry.getKey().equals("severity")))
                                .map(
                                        entry ->
                                                "\n" + WordUtils.capitalizeFully(entry.getKey()) + ": " + entry.getValue())
                                .collect(Collectors.joining("\n", "\n", ""));
            }
        }

        log.error("Failed to start {} {}", execution.getType(), execution.getId(), failure);
        execution.updateStatus(ExecutionStatus.TERMINAL);
        executionRepository.updateStatus(execution);
        executionRepository.cancel(execution.getType(), execution.getId(), canceledBy, reason);
    }

    private PipelineExecution parse(ExecutionType type, Map<String, Object> config) {
        switch (type) {
            case PIPELINE:
                return parsePipeline(config);
            case ORCHESTRATION:
                return parseOrchestration(config);
            default:
                throw new IllegalStateException("Unexpected execution type " + type.toString());
        }
    }

    private PipelineExecution parsePipeline(Map<String, Object> config) {
        // TODO: can we not just annotate the class properly to avoid all this?
        return new PipelineBuilder(getString(config, "name"))
                .withNamespace(getString(config, "namespace"))
                .withName(getString(config, "name"))
                .withPipelineConfigId(Long.parseLong(getString(config, "id")))
                .withTrigger(objectMapper.convertValue(config.get("trigger"), Trigger.class))
                .withStages(getList(config, "stages"))
                .withLimitConcurrent(getBoolean(config, "limitConcurrent"))
                .withMaxConcurrentExecutions(getInt(config, "maxConcurrentExecutions"))
                .withKeepWaitingPipelines(getBoolean(config, "keepWaitingPipelines"))
                .withNotifications(getList(config, "notifications"))
                .withInitialConfig(getMap(config, "initialConfig"))
                .withOrigin(getString(config, "origin"))
                .withStartTimeExpiry(getString(config, "startTimeExpiry"))
                .withSpelEvaluator(getString(config, "spelEvaluator"))
                .withTemplateVariables(getMap(config, "templateVariables"))
                .build();
    }

    private PipelineExecution parseOrchestration(Map<String, Object> config) {
        PipelineExecutionImpl orchestration =
                PipelineExecutionImpl.newOrchestration(getString(config, "application"));
        if (config.containsKey("name")) {
            orchestration.setRemark(getString(config, "name"));
        }
        if (config.containsKey("description")) {
            orchestration.setRemark(getString(config, "description"));
        }

        for (Map<String, Object> context : getList(config, "stages")) {
            String type = context.remove("type").toString();

            String providerType = getString(context, "providerType");
            if (providerType != null && !providerType.equals("aws") && !providerType.equals("titus")) {
                type += format("_%s", providerType);
            }

            // TODO: need to check it's valid?
            StageExecutionImpl stage = new StageExecutionImpl(orchestration, type, context);
            orchestration.getStages().add(stage);
        }

        if (config.get("trigger") != null) {
            Trigger trigger = objectMapper.convertValue(config.get("trigger"), Trigger.class);
            orchestration.setTrigger(trigger);
        }

        orchestration.setBuildTime(Instant.now(clock));

        orchestration.setOrigin((String) config.getOrDefault("origin", "unknown"));
        orchestration.setStartTimeExpiry((Instant) config.get("startTimeExpiry"));
        orchestration.setSpelEvaluator(getString(config, "spelEvaluator"));

        return orchestration;
    }

    /**
     * Persist the initial execution configuration.
     */
    private void persistExecution(PipelineExecution execution) {
        applicationEventPublisher.publishEvent(new BeforeInitialExecutionPersist(this, execution));
        Long id = executionRepository.store(execution);
        ((PipelineExecutionImpl) execution).setId(id);
    }

    private static boolean getBoolean(Map<String, ?> map, String key) {
        return parseBoolean(getString(map, key));
    }

    private static int getInt(Map<String, ?> map, String key) {
        return map.containsKey(key) ? parseInt(getString(map, key)) : 0;
    }

    private static String getString(Map<String, ?> map, String key) {
        return map.containsKey(key) ? map.get(key).toString() : null;
    }

    private static <K, V> Map<K, V> getMap(Map<String, ?> map, String key) {
        Map<K, V> result = (Map<K, V>) map.get(key);
        return result == null ? emptyMap() : result;
    }

    private static List<Map<String, Object>> getList(Map<String, ?> map, String key) {
        List<Map<String, Object>> result = (List<Map<String, Object>>) map.get(key);
        return result == null ? emptyList() : result;
    }

    private static <E extends Enum<E>> E getEnum(Map<String, ?> map, String key, Class<E> type) {
        String value = (String) map.get(key);
        return value != null ? Enum.valueOf(type, value) : null;
    }
}
