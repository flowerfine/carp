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

import java.util.*;

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
        IReport report = new IReport()
                .setId(UUIDUtil.randomUUId())
                .setWorkflowStatus(getStatusCenter().getWorkflow().export())
                .setReports(nodeReports())
                .setMessages(getMessageCenter().export());
        report.setInputs(Objects.isNull(ioCenter.getInputs()) ? new WorkflowInputs() : new WorkflowInputs(ioCenter.getInputs()));
        report.setOutputs(Objects.isNull(ioCenter.getOutputs()) ? new WorkflowOutputs() : new WorkflowOutputs(ioCenter.getOutputs()));
        return report;
    }

    private Map<String, NodeReport> nodeReports() {
        Map<String, NodeReport> reports = new HashMap<>();
        Map<String, StatusData> statuses = getStatusCenter().exportNodeStatus();
        Map<String, List<Snapshot>> snapshotsMap = getSnapshotCenter().export();
        for (Map.Entry<String, StatusData> entry : statuses.entrySet()) {
            String nodeId = entry.getKey();
            StatusData status = entry.getValue();
            List<Snapshot> nodeSnapshots = snapshotsMap.getOrDefault(nodeId, new ArrayList<>());
            NodeReport nodeReport = new NodeReport();
            BeanUtil.copyProperties(status, nodeReport);
            nodeReport.setId(nodeId);
            nodeReport.setSnapshots(nodeSnapshots);
            reports.put(nodeId, nodeReport);
        }
        return reports;
    }
}
