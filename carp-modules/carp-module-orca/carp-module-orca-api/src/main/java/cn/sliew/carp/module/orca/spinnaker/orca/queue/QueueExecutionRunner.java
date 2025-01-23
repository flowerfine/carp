package cn.sliew.carp.module.orca.spinnaker.orca.queue;

import cn.sliew.carp.module.orca.spinnaker.api.executions.ExecutionRunner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;

public class QueueExecutionRunner implements ExecutionRunner {

    private Queue queue;

    public QueueExecutionRunner(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void start(PipelineExecution execution) {
        queue.push(new Messages.StartExecution(execution));
    }

    @Override
    public void reschedule(PipelineExecution execution) {
        queue.push(new Messages.RescheduleExecution(execution));
    }

    @Override
    public void restart(PipelineExecution execution, Long stageId) {
        queue.push(new Messages.RestartStage(execution, stageId, null));
    }

    @Override
    public void unpause(PipelineExecution execution) {
        queue.push(new Messages.ResumeExecution(execution));
    }

    @Override
    public void cancel(PipelineExecution execution, String reason) {
        queue.push(new Messages.CancelExecution(execution, null, reason));
    }
}
