package cn.sliew.carp.module.orca.spinnaker.keiko.core;

public interface MessageHandler<M extends Message> {

    Class<M> getMessageType();

    Queue getQueue();

    default void invoke(Message message) {
        if (getMessageType().isAssignableFrom(message.getClass())) {
            handle((M) message);
        } else {
            throw new IllegalArgumentException("Unsupported message type ${message.javaClass.simpleName}");
        }
    }

    void handle(M message);
}
