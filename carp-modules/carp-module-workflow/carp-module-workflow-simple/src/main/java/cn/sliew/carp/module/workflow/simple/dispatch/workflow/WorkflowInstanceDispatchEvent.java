package cn.sliew.carp.module.workflow.simple.dispatch.workflow;

import cn.sliew.carp.module.workflow.simple.enums.WorkflowInstanceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class WorkflowInstanceDispatchEvent {

    private Long workflowInstanceId;
    private Long workflowStepDefinitionId;
    private WorkflowInstanceStatus workflowInstanceStatus;
}
