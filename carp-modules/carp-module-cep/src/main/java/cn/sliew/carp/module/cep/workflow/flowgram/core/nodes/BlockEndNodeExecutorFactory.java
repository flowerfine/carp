package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class BlockEndNodeExecutorFactory implements INodeExecutorFactory {

    public static final BlockEndNodeExecutorFactory INSTANCE = new BlockEndNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new BlockEndExecutor();
    }
}
