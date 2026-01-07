package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WorkflowNodeMetaSchema implements IWorkflowNodeMetaSchema {

    private PositionSchema position;
    private PositionSchema canvasPosition;
}
