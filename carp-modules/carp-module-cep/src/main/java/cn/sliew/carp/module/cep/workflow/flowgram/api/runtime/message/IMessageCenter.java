package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
public abstract class IMessageCenter {

    public abstract void init();

    public abstract void dispose();

    public abstract IMessage log(MessageData data);

    public abstract IMessage info(MessageData data);

    public abstract IMessage debug(MessageData data);

    public abstract IMessage error(MessageData data);

    public abstract IMessage warn(MessageData data);

    public abstract Map<WorkflowMessageType, List<IMessage>> export();

    @Data
    @Accessors(chain = true)
    public static class MessageData {

        private String message;
        private String nodeID;
        private Long timestamp;
    }

    @Data
    public static class IMessage extends MessageData {

        private String id;
        private WorkflowMessageType type;
    }
}
