package cn.sliew.carp.server.temporal.hello;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloWorkflow {

    String WORKFLOW_METHOD = "hello";
    String WORKFLOW_TASK_QUEUE = "test";

    /**
     * workflow method 默认是：HelloWorkflow
     * @return
     */
    @WorkflowMethod(name = WORKFLOW_METHOD)
    String sayHello();
}
