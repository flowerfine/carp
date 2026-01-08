package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class HTTPNodeExecutorFactory implements INodeExecutorFactory {

    public static final HTTPNodeExecutorFactory INSTANCE = new HTTPNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new HTTPExecutor();
    }
}
