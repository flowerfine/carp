package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.ITask;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;

public abstract class IEngine {

    public abstract ITask invoke(IWorkflowSchema schema, WorkflowInputs inputs);

    public abstract void executeNode(IContext context, INode node);
}
