package cn.sliew.carp.server.temporal.hello;

import cn.sliew.carp.framework.workflow.temporal.TemporalUtil;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@WorkflowImpl(taskQueues = HelloWorkflow.WORKFLOW_TASK_QUEUE)
public class HelloWorkflowImpl implements HelloWorkflow {

    private HelloActivity activity =
            Workflow.newActivityStub(
                    HelloActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(2))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setInitialInterval(Duration.ofSeconds(1))
                                    .setMaximumInterval(Duration.ofSeconds(10))
                                    .setBackoffCoefficient(2)
                                    .setMaximumAttempts(1) //0 无限重试, 1 不重试, 负值 异常
                                    .build())
                            .build());

    @Override
    public String sayHello() {
        TemporalUtil.logWorkflow(log, Workflow.getInfo());
        return activity.hello();
    }
}
