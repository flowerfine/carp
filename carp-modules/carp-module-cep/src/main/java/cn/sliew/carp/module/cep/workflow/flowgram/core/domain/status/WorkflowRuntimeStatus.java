package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.status;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.IStatus;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.StatusData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.WorkflowStatus;
import lombok.Getter;

import java.util.Objects;

@Getter
public class WorkflowRuntimeStatus extends IStatus {

    private WorkflowStatus status;
    private Long startTime;
    private Long endTime;

    public WorkflowRuntimeStatus() {
        setId(UUIDUtil.randomUUId());
        this.status = WorkflowStatus.PENDING;
    }

    public boolean terminated() {
        switch (status) {
            case SUCCEEDED:
            case FAILED:
            case CANCELLED:
                return true;
            default:
                return false;
        }
    }

    public Long timeCost() {
        if (Objects.isNull(startTime)) {
            return 0L;
        }
        if (Objects.nonNull(endTime)) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public void process() {
        status = WorkflowStatus.PROCESSING;
        startTime = System.currentTimeMillis();

    }

    @Override
    public void success() {
        if (terminated()) {
            return;
        }

        status = WorkflowStatus.SUCCEEDED;
        endTime = System.currentTimeMillis();
    }

    @Override
    public void fail() {
        if (terminated()) {
            return;
        }

        status = WorkflowStatus.FAILED;
        endTime = System.currentTimeMillis();
    }

    @Override
    public void cancel() {
        if (terminated()) {
            return;
        }

        status = WorkflowStatus.CANCELLED;
        endTime = System.currentTimeMillis();
    }

    @Override
    public StatusData export() {
        return new StatusData()
                .setStatus(status)
                .setTerminated(terminated())
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setTimeCost(timeCost());
    }

    public static WorkflowRuntimeStatus create() {
        return new WorkflowRuntimeStatus();
    }
}
