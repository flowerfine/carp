package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class EndNodeExecutorFactory implements INodeExecutorFactory {

    public static final EndNodeExecutorFactory INSTANCE = new EndNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new EndExecutor();
    }
}
