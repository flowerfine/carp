package cn.sliew.carp.module.workflow.simple.dispatch.workflow;

public interface WorkflowInstanceDispatchManager {

    void dispatch(WorkflowInstanceDispatchEvent event);
}
