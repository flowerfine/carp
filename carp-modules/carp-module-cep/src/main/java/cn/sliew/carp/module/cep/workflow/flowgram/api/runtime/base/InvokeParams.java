package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import lombok.Data;

@Data
public class InvokeParams {

    private IWorkflowSchema schema;
    private WorkflowInputs inputs;
}
