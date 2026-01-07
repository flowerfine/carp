package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

@Data
@Accessors(chain = true)
public class TaskParams {

    private CompletableFuture<WorkflowOutputs> processing;
    private IContext context;
}
