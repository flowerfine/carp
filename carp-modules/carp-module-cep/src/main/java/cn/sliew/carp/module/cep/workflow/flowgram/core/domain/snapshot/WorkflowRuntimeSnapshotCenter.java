package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.snapshot;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshotCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.Snapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.SnapshotData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowRuntimeSnapshotCenter extends ISnapshotCenter {

    private ArrayList<ISnapshot> snapshots;

    public WorkflowRuntimeSnapshotCenter() {
        setId(UUIDUtil.randomUUId());
    }

    @Override
    public ISnapshot create(SnapshotData snapshotData) {
        WorkflowRuntimeSnapshot snapshot = WorkflowRuntimeSnapshot.create(snapshotData);
        snapshots.add(snapshot);
        return snapshot;
    }

    @Override
    public List<Snapshot> exportAll() {
        return snapshots.stream().map(ISnapshot::export).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Snapshot>> export() {
        Map<String, List<Snapshot>> map = new HashMap<>();
        for (Snapshot snapshot: exportAll()) {
            List<Snapshot> snapshots = map.computeIfAbsent(snapshot.getNodeID(), key -> new ArrayList<>());
            snapshots.add(snapshot);
        }
        return map;
    }

    @Override
    public void init() {
        this.snapshots = new ArrayList<>();
    }

    @Override
    public void dispose() {

    }
}
