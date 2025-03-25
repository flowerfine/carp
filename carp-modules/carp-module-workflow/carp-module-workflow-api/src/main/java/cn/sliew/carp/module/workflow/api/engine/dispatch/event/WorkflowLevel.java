package cn.sliew.carp.module.workflow.api.engine.dispatch.event;

public interface WorkflowLevel extends NamespaceAware {

        String getType();

        Long getWorkflowInstanceId();
    }