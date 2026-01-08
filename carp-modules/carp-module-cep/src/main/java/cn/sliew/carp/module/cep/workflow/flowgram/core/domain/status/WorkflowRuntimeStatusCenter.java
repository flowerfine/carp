package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.status;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.IStatus;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.IStatusCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.StatusData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.WorkflowStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class WorkflowRuntimeStatusCenter extends IStatusCenter {

    private Map<String, IStatus> nodeStatus;
    private Long startTime;
    private Long endTime;

    @Override
    public IStatus nodeStatus(String nodeId) {
        return nodeStatus.computeIfAbsent(nodeId, key -> WorkflowRuntimeStatus.create());
    }

    @Override
    public void init() {
        setWorkflow(WorkflowRuntimeStatus.create());
        this.nodeStatus = new HashMap<>();
    }

    @Override
    public void dispose() {

    }

    @Override
    public String[] getStatusNodeIDs(WorkflowStatus status) {
        List<String> nodeIds = new ArrayList<>();
        for (Map.Entry<String, IStatus> entry : nodeStatus.entrySet()) {
            if (entry.getValue().getStatus() == status) {
                nodeIds.add(entry.getKey());
            }
        }
        return nodeIds.toArray(new String[0]);
    }

    @Override
    public Map<String, StatusData> exportNodeStatus() {
        Map<String, StatusData> statusDataMap = new HashMap<>();
        for (Map.Entry<String, IStatus> entry : nodeStatus.entrySet()) {
            statusDataMap.put(entry.getKey(), entry.getValue().export());
        }
        return statusDataMap;
    }
}
