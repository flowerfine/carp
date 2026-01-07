package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.task;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.WorkflowStatus;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.ITask;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.TaskParams;

public class WorkflowRuntimeTask extends ITask {

    public WorkflowRuntimeTask(TaskParams params) {
        setId(UUIDUtil.randomUUId());
        setContext(params.getContext());
        setProcessing(params.getProcessing());
    }

    @Override
    public void cancel() {
        getContext().getStatusCenter().getWorkflow().cancel();
        String[] statusNodeIDs = getContext().getStatusCenter().getStatusNodeIDs(WorkflowStatus.PROCESSING);
        for (String statusNodeID : statusNodeIDs) {
            getContext().getStatusCenter().nodeStatus(statusNodeID).cancel();
        }
    }

    public static WorkflowRuntimeTask create(TaskParams params) {
        return new WorkflowRuntimeTask(params);
    }
}
