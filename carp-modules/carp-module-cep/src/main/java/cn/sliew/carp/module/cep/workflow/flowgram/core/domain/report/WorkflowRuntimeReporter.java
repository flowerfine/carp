package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.report;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.iocenter.IIOCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter.IReport;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter.IReporter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter.NodeReport;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshotCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.Snapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.IStatusCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.StatusData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowRuntimeReporter extends IReporter {

    private IIOCenter ioCenter;

    public WorkflowRuntimeReporter(IIOCenter ioCenter,
                                   ISnapshotCenter snapshotCenter,
                                   IStatusCenter statusCenter,
                                   IMessageCenter messageCenter) {
        this.ioCenter = ioCenter;
        setSnapshotCenter(snapshotCenter);
        setStatusCenter(statusCenter);
        setMessageCenter(messageCenter);
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public IReport export() {
        return new IReport()
                .setId(UUIDUtil.randomUUId())
                .setInputs(ioCenter.getInputs())
                .setOutputs(ioCenter.getOutputs())
                .setWorkflowStatus(getStatusCenter().getWorkflow().export())
                .setReports(nodeReports())
                .setMessages(getMessageCenter().export());
    }

    private Map<String, NodeReport> nodeReports() {
        Map<String, NodeReport> reports = new HashMap<>();
        Map<String, StatusData> statusDataMap = getStatusCenter().exportNodeStatus();
        Map<String, List<Snapshot>> snapshotsMap = getSnapshotCenter().export();
        for (Map.Entry<String, StatusData> entry : statusDataMap.entrySet()) {
            String nodeId = entry.getKey();
            StatusData statusData = entry.getValue();
            List<Snapshot> snapshots = snapshotsMap.getOrDefault(nodeId, new ArrayList<>());
            NodeReport nodeReport = new NodeReport();
            BeanUtil.copyProperties(statusData, nodeReport);
            nodeReport.setId(nodeId);
            nodeReport.setSnapshots(snapshots);
            reports.put(nodeId, nodeReport);
        }
        return reports;
    }
}
