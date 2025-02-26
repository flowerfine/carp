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
package cn.sliew.carp.module.dag.queue.handler;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.repository.entity.DagStep;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.exception.ExceptionVO;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.dag.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilderUtil;
import cn.sliew.carp.module.workflow.stage.model.util.WorkflowUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
public class StartStepHandler extends AbstractDagMessageHandler<Messages.StartStep> implements StepBuilderAware {

    private static final Duration RETRY_DELAY = Duration.ofSeconds(15);

    @Autowired
    private StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagStepService dagStepService;

    @Override
    public Class<Messages.StartStep> getMessageType() {
        return Messages.StartStep.class;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    public void handle(Messages.StartStep message) {
        withStep(message, stepInstance -> {
            try {
                DagInstanceComplexDTO dagInstanceComplexDTO = dagInstanceComplexService.selectOne(message.getDagId());
                DAG<WorkflowStepInstance> dag = WorkflowUtil.buildDag(dagInstanceComplexDTO);

                if (DagExecutionUtil.anyUpstreamStepsFailed(dag, stepInstance)) {
                    getLog().warn("Workflow Instance (namespace: {}, type {}, id {}) try to start stepId: {} but something upstream had failed",
                            message.getNamespace(), message.getType(), message.getDagId(), stepInstance.getId());
                    push(new Messages.CompleteWorkflow(message));
                } else if (DagExecutionUtil.allUpstreamStepsComplete(dag, stepInstance)) {
                    if (StringUtils.equalsIgnoreCase(stepInstance.getStatus(), ExecutionStatus.NOT_STARTED.name()) == false) {
                        getLog().warn("Workflow Instance (namespace: {}, type {}, id {}) ignore start stepId: {} as already {}",
                                message.getNamespace(), message.getType(), message.getDagId(), message.getStepId(), stepInstance.getStatus());
                    } else if (shouldSkip(stepInstance)) {
                        push(new Messages.SkipStep(message));
                    } else if (isAfterStartTimeExpiry(stepInstance)) {
                        getLog().warn("Workflow Instance (namespace: {}, type {}, id {}) skip stepId: {} because its start time is after TTL",
                                message.getNamespace(), message.getType(), message.getDagId(), message.getStepId());
                        push(new Messages.SkipStep(stepInstance));
                    } else {
                        try {
                            plan(stepInstance);

                            LambdaUpdateWrapper<DagStep> updateWrapper = Wrappers.lambdaUpdate(DagStep.class)
                                    .eq(DagStep::getId, stepInstance.getId())
                                    .set(DagStep::getStatus, ExecutionStatus.RUNNING.name())
                                    .set(DagStep::getStartTime, new Date())
                                    .set(DagStep::getBody, Objects.isNull(stepInstance.getBody()) ? null : stepInstance.getBody().toString());
                            dagStepService.update(updateWrapper);

                            start(stepInstance);
                        } catch (Exception e) {
                            handlePlanningException(message, stepInstance, e);
                        }
                    }
                } else {
                    getLog().info("Workflow Instance (namespace: {}, type {}, id {})  requeue stepId: {} as upstream steps are not yet complete",
                            message.getNamespace(), message.getType(), message.getDagId(), message.getStepId());
                    push(message, RETRY_DELAY);
                }
            } catch (Exception e) {
                handleUnexpectedException(message, stepInstance, e);
            }
        });
    }

    private boolean shouldSkip(WorkflowStepInstance stepInstance) {
        return false;
    }

    private boolean isAfterStartTimeExpiry(WorkflowStepInstance stepInstance) {
        return false;
    }

    private void plan(WorkflowStepInstance stepInstance) {
        StageDefinitionBuilder builder = builder(stepInstance);
        StageDefinitionBuilderUtil.buildTasks(builder, stepInstance);
    }

    private void start(WorkflowStepInstance stepInstance) {
        // orca 的处理逻辑：before-stages -> tasks[0] -> after-stages
        // orca 在执行时不会依赖 pipeline-template，stage 执行时可以动态新增新的 before & after stage，
        // 而 carp 的数据结构是强 config -> instance，动态新增 step 时会因为 step instance 缺少 step config
        // 而异常。后续将 config 由根据 step_config_id 关联，变成 body 时会好一些，但是也不好新增 link
        // 因此 carp 只能实现 dag 级别的 before & after 节点，不支持 step 级别的 before & after 节点。
        // 或者说无论是 dag 或 step 的 before & after 节点都必须是在 canvas 提前创建好的。执行即可
        // 按照 task 的定义顺序，挨个执行 tasks。
        TaskExecutionImpl task = DagExecutionUtil.firstTask(stepInstance);
        if (Objects.nonNull(task)) {
            push(new Messages.StartTask(stepInstance, task));
        } else {
            push(new Messages.CompleteStep(stepInstance));
        }
    }

    private void handlePlanningException(Messages.StartStep message, WorkflowStepInstance stepInstance, Exception e) {
        ExceptionVO exceptionVO = handleException(stepInstance.getNode().getStepName(), e);
        if (Objects.nonNull(exceptionVO) && Boolean.TRUE.equals(exceptionVO.isRetryable())) {
            // todo 处理消息 retry count
            push(message, RETRY_DELAY);
        } else {
            getLog().error("Dag Instance (namespace: {}, type {}, id {}) run error! stepId: {}",
                    message.getNamespace(), message.getType(), message.getDagId(), message.getStepId(), e);
//            stage.getContext().put("exception", exceptionVO);
//            stage.getContext().put("beforeStagePlanningFailed", true);
//            getRepository().storeStage(stage);

            push(new Messages.CompleteStep(message));
        }
    }


    private void handleUnexpectedException(Messages.StartStep message, WorkflowStepInstance dagStepDTO, Exception e) {
        getLog().error("Dag Instance (namespace: {}, type {}, id {}) run error! stepId: {}",
                message.getNamespace(), message.getType(), message.getDagId(), message.getStepId(), e);
        ExceptionVO exceptionVO = handleException(dagStepDTO.getNode().getStepName(), e);
//        stage.getContext().put("exception", exceptionVO);
//        stage.getContext().put("beforeStagePlanningFailed", true);

//        getRepository().storeStage(stage);
        push(new Messages.CompleteStep(message));
    }
}
