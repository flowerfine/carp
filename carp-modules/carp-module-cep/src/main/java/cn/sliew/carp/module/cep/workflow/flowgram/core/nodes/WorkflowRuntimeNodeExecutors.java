package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

import java.util.ArrayList;
import java.util.List;

public class WorkflowRuntimeNodeExecutors {

    public static final List<INodeExecutorFactory> NODE_EXECUTORS = new ArrayList<>();

    static {
        NODE_EXECUTORS.add(StartNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(EndNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(HTTPNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(CodeNodeExecutorFactory.INSTANCE);

        NODE_EXECUTORS.add(ConditionNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(LoopNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(ContinueNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(BreakNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(BlockStartNodeExecutorFactory.INSTANCE);
        NODE_EXECUTORS.add(BlockEndNodeExecutorFactory.INSTANCE);
    }
}
