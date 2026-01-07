package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot;

import lombok.Data;

@Data
public abstract class ISnapshot {

    private String id;
    private SnapshotData data;

    public abstract void update(SnapshotData data);

    public abstract boolean validate();

    public abstract Snapshot export();
}
