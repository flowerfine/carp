package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import lombok.Data;

import java.util.concurrent.CompletableFuture;

@Data
public abstract class ITask {

    private String id;
    private CompletableFuture<WorkflowOutputs> processing;
    private IContext context;

    public abstract void cancel();
}
