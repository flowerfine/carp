package cn.sliew.carp.module.workflow.api.service.param;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class WorkflowRunParam {

    private Long id;
    private JsonNode globalVariable;
}
