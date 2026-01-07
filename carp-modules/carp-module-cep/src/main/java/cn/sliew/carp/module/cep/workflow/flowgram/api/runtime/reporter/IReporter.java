package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshotCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.IStatusCenter;
import lombok.Data;

@Data
public abstract class IReporter {

    private ISnapshotCenter snapshotCenter;
    private IStatusCenter statusCenter;
    private IMessageCenter messageCenter;

    public abstract void init();
    public abstract void dispose();
    public abstract IReport export();
}
