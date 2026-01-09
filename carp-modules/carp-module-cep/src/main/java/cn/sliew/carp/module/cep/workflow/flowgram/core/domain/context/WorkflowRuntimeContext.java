package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.context;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.cache.ICache;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.ContextData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.state.IState;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.cache.WorkflowRuntimeCache;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.document.WorkflowRuntimeDocument;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.iocenter.WorkflowRuntimeIOCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.message.WorkflowRuntimeMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.report.WorkflowRuntimeReporter;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.snapshot.WorkflowRuntimeSnapshotCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.state.WorkflowRuntimeState;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.status.WorkflowRuntimeStatusCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.variable.WorkflowRuntimeVariableStore;

import java.util.ArrayList;
import java.util.List;

public class WorkflowRuntimeContext extends IContext {

    private List<IContext> subContexts = new ArrayList<>();

    public WorkflowRuntimeContext(ContextData data) {
        setId(UUIDUtil.randomUUId());
        setCache(data.getCache());
        setDocument(data.getDocument());
        setVariableStore(data.getVariableStore());
        setState(data.getState());
        setIoCenter(data.getIoCenter());
        setSnapshotCenter(data.getSnapshotCenter());
        setStatusCenter(data.getStatusCenter());
        setMessageCenter(data.getMessageCenter());
        setReporter(data.getReporter());
    }

    @Override
    public void init(IWorkflowSchema schema, WorkflowInputs inputs) {
        getCache().init();
        getDocument().init(schema);
        getVariableStore().init();
        getState().init(schema);
        getIoCenter().init(inputs);
        getSnapshotCenter().init();
        getStatusCenter().init();
        getMessageCenter().init();
        getReporter().init();
    }

    @Override
    public void dispose() {
        subContexts.forEach(IContext::dispose);
        getCache().dispose();
        getDocument().dispose();
        getVariableStore().dispose();
        getState().dispose();
        getIoCenter().dispose();
        getSnapshotCenter().dispose();
        getStatusCenter().dispose();
        getMessageCenter().dispose();
        getReporter().dispose();
    }

    @Override
    public IContext sub() {
        ICache cache = new WorkflowRuntimeCache();
        IVariableStore variableStore = new WorkflowRuntimeVariableStore();
        variableStore.setParent(getVariableStore());
        IState state = new WorkflowRuntimeState();
        state.setVariableStore(variableStore);
        ContextData contextData = new ContextData()
                .setCache(cache)
                .setDocument(getDocument())
                .setIoCenter(getIoCenter())
                .setSnapshotCenter(getSnapshotCenter())
                .setStatusCenter(getStatusCenter())
                .setMessageCenter(getMessageCenter())
                .setReporter(getReporter())
                .setVariableStore(variableStore)
                .setState(state);
        WorkflowRuntimeContext subContext = new WorkflowRuntimeContext(contextData);
        subContexts.add(subContext);
        subContext.getCache().init();
        subContext.getVariableStore().init();
        subContext.getState().init(null);
        return subContext;
    }

    public static WorkflowRuntimeContext create() {
        WorkflowRuntimeCache cache = new WorkflowRuntimeCache();
        WorkflowRuntimeDocument document = new WorkflowRuntimeDocument();
        WorkflowRuntimeVariableStore variableStore = new WorkflowRuntimeVariableStore();
        WorkflowRuntimeState state = new WorkflowRuntimeState();
        state.setVariableStore(variableStore);
        WorkflowRuntimeIOCenter ioCenter = new WorkflowRuntimeIOCenter();
        WorkflowRuntimeSnapshotCenter snapshotCenter = new WorkflowRuntimeSnapshotCenter();
        WorkflowRuntimeStatusCenter statusCenter = new WorkflowRuntimeStatusCenter();
        WorkflowRuntimeMessageCenter messageCenter = new WorkflowRuntimeMessageCenter();
        WorkflowRuntimeReporter reporter = new WorkflowRuntimeReporter(ioCenter, snapshotCenter, statusCenter, messageCenter);

        return new WorkflowRuntimeContext(
                new ContextData()
                        .setCache(cache)
                        .setDocument(document)
                        .setVariableStore(variableStore)
                        .setState(state)
                        .setIoCenter(ioCenter)
                        .setSnapshotCenter(snapshotCenter)
                        .setStatusCenter(statusCenter)
                        .setMessageCenter(messageCenter)
                        .setReporter(reporter)
        );
    }
}
