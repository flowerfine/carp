package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class ConditionNodeExecutorFactory implements INodeExecutorFactory {

    public static final ConditionNodeExecutorFactory INSTANCE = new ConditionNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new ConditionExecutor();
    }
}
