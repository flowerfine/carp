package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.message;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.WorkflowMessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowRuntimeMessageCenter extends IMessageCenter {

    private Map<WorkflowMessageType, List<IMessage>> messages;

    @Override
    public void init() {
        messages = new HashMap<>();
        messages.computeIfAbsent(WorkflowMessageType.LOG, key -> new ArrayList<>());
        messages.computeIfAbsent(WorkflowMessageType.INFO, key -> new ArrayList<>());
        messages.computeIfAbsent(WorkflowMessageType.DEBUG, key -> new ArrayList<>());
        messages.computeIfAbsent(WorkflowMessageType.ERROR, key -> new ArrayList<>());
        messages.computeIfAbsent(WorkflowMessageType.WARN, key -> new ArrayList<>());
    }

    @Override
    public void dispose() {

    }

    @Override
    public IMessage log(MessageData data) {
        IMessage message = WorkflowRuntimeMessage.create(WorkflowMessageType.LOG, data);
        List<IMessage> logMessageList = messages.computeIfAbsent(WorkflowMessageType.LOG, key -> new ArrayList<>());
        logMessageList.add(message);
        return message;
    }

    @Override
    public IMessage info(MessageData data) {
        IMessage message = WorkflowRuntimeMessage.create(WorkflowMessageType.INFO, data);
        List<IMessage> logMessageList = messages.computeIfAbsent(WorkflowMessageType.INFO, key -> new ArrayList<>());
        logMessageList.add(message);
        return message;
    }

    @Override
    public IMessage debug(MessageData data) {
        IMessage message = WorkflowRuntimeMessage.create(WorkflowMessageType.DEBUG, data);
        List<IMessage> logMessageList = messages.computeIfAbsent(WorkflowMessageType.DEBUG, key -> new ArrayList<>());
        logMessageList.add(message);
        return message;
    }

    @Override
    public IMessage error(MessageData data) {
        IMessage message = WorkflowRuntimeMessage.create(WorkflowMessageType.ERROR, data);
        List<IMessage> logMessageList = messages.computeIfAbsent(WorkflowMessageType.ERROR, key -> new ArrayList<>());
        logMessageList.add(message);
        return message;
    }

    @Override
    public IMessage warn(MessageData data) {
        IMessage message = WorkflowRuntimeMessage.create(WorkflowMessageType.WARN, data);
        List<IMessage> logMessageList = messages.computeIfAbsent(WorkflowMessageType.WARN, key -> new ArrayList<>());
        logMessageList.add(message);
        return message;
    }

    @Override
    public Map<WorkflowMessageType, List<IMessage>> export() {
        return new HashMap<>(messages);
    }
}
