package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.message;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.WorkflowMessageType;

import java.util.Objects;

public class WorkflowRuntimeMessage {

    public static IMessageCenter.IMessage create(WorkflowMessageType type, IMessageCenter.MessageData data) {
        IMessageCenter.IMessage message = new IMessageCenter.IMessage();
        BeanUtil.copyProperties(data, message);
        message.setId(UUIDUtil.randomUUId());
        message.setType(type);
        if (Objects.isNull(message.getTimestamp())) {
            message.setTimestamp(System.currentTimeMillis());
        }
        return message;
    }
}
