package cn.sliew.carp.module.workflow.simple.tasks.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@Jacksonized
public class WorkflowTaskRunRet {

    @Builder.Default
    private WorkflowTaskRunResult result = WorkflowTaskRunResult.builder().build();
    @Builder.Default
    private Map<String, Object> output = new HashMap<>(0);

    private Object data;
}
