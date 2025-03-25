package cn.sliew.carp.module.workflow.simple.tasks.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class WorkflowTaskRunResult {

    @Builder.Default
    private String name = "";
    @Builder.Default
    private String message = "";
    @Builder.Default
    private String action = "";
    @Builder.Default
    private WorkflowTaskRunResultStatus status = WorkflowTaskRunResultStatus.SUCCESS;

}
