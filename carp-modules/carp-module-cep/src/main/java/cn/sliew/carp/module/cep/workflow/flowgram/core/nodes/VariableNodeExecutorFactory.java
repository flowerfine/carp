package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class VariableNodeExecutorFactory implements INodeExecutorFactory {

    public static final VariableNodeExecutorFactory INSTANCE = new VariableNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new VariableExecutor();
    }
}
