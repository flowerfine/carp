package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class StartNodeExecutorFactory implements INodeExecutorFactory {

    public static final StartNodeExecutorFactory INSTANCE = new StartNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new StartExecutor();
    }
}
