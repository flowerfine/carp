package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class ContinueNodeExecutorFactory implements INodeExecutorFactory {

    public static final ContinueNodeExecutorFactory INSTANCE = new ContinueNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new ContinueExecutor();
    }
}
