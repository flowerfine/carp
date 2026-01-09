package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

public class CodeNodeExecutorFactory implements INodeExecutorFactory {

    public static final CodeNodeExecutorFactory INSTANCE = new CodeNodeExecutorFactory();

    @Override
    public INodeExecutor newExecutor() {
        return new CodeExecutor();
    }
}
