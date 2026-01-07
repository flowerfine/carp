package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.cache.ICache;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IDocument;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.iocenter.IIOCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter.IReporter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshotCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.state.IState;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.IStatusCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ContextData {

    private ICache cache;
    private IVariableStore variableStore;
    private IState state;
    private IDocument document;
    private IIOCenter ioCenter;
    private ISnapshotCenter snapshotCenter;
    private IStatusCenter statusCenter;
    private IMessageCenter messageCenter;
    private IReporter reporter;
}
