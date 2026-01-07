package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.ITask;

public abstract class IEngine {

    public abstract ITask invoke(InvokeParams params);

    public abstract void executeNode(IContext context, INode node);
}
