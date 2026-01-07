package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WorkflowEdgeSchema implements IWorkflowEdgeSchema {

    private String sourceNodeID;
    private String targetNodeID;
    private String sourcePortID;
    private String targetPortID;
}
