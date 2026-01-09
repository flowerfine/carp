package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class BlockStartNodeExecutorFactory implements INodeExecutorFactory {

    public static final BlockStartNodeExecutorFactory INSTANCE = new BlockStartNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new BlockStartExecutor();
    }
}
