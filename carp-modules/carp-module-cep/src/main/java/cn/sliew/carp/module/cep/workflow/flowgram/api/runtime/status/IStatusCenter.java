package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status;

import lombok.Data;

import java.util.Map;

@Data
public abstract class IStatusCenter {

    private IStatus workflow;

    public abstract IStatus nodeStatus(String nodeId);

    public abstract void init();

    public abstract void dispose();

    public abstract String[] getStatusNodeIDs(WorkflowStatus status);

    public abstract Map<String, StatusData> exportNodeStatus();
}
