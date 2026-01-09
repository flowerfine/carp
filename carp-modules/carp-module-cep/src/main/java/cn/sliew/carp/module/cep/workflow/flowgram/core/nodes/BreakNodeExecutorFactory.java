package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class BreakNodeExecutorFactory implements INodeExecutorFactory {

    public static final BreakNodeExecutorFactory INSTANCE = new BreakNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new BreakExecutor();
    }
}
