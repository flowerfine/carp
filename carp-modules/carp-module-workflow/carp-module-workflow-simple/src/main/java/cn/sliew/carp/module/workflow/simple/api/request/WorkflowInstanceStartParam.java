package cn.sliew.carp.module.workflow.simple.api.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class WorkflowInstanceStartParam {

    private Long workflowDefinitionId;
    private JsonNode globalVariable;
}
