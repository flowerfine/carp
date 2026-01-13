package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class LoopNodeExecutorFactory implements INodeExecutorFactory {

    public static final LoopNodeExecutorFactory INSTANCE = new LoopNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new LoopExecutor();
    }
}
