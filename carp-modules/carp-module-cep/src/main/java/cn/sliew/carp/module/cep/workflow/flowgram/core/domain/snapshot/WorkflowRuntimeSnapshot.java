package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.snapshot;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.Snapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.SnapshotData;

import java.util.Objects;

public class WorkflowRuntimeSnapshot extends ISnapshot {

    public WorkflowRuntimeSnapshot(SnapshotData data) {
        setId(UUIDUtil.randomUUId());
        setData(data);
    }

    @Override
    public void update(SnapshotData data) {
        CopyOptions copyOptions = CopyOptions.create(SnapshotData.class, true);
        BeanUtil.copyProperties(data, getData(), copyOptions);
    }

    @Override
    public boolean validate() {
        SnapshotData data = getData();
        return Objects.nonNull(data.getNodeID())
                && Objects.nonNull(data.getInputs())
                && Objects.nonNull(data.getOutputs())
                && Objects.nonNull(data.getData());
    }

    @Override
    public Snapshot export() {
        Snapshot snapshot = new Snapshot();
        BeanUtil.copyProperties(getData(), snapshot);
        snapshot.setId(getId());
        return snapshot;
    }

    public static WorkflowRuntimeSnapshot create(SnapshotData data) {
        return new WorkflowRuntimeSnapshot(data);
    }
}
