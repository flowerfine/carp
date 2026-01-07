package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public abstract class ISnapshotCenter {

    private String id;

    public abstract ISnapshot create(SnapshotData snapshotData);
    public abstract List<Snapshot> exportAll();
    public abstract Map<String, List<Snapshot>> export();
    public abstract void init();
    public abstract void dispose();
}
