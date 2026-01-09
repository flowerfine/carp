package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import lombok.Data;

@Data
public abstract class IContext extends ContextData {

    private String id;

    public abstract void init(IWorkflowSchema schema, WorkflowInputs inputs);

    public abstract void dispose();

    public abstract IContext sub();
}
