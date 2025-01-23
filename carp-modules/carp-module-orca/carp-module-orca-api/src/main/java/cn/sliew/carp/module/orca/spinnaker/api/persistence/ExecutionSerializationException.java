package cn.sliew.carp.module.orca.spinnaker.api.persistence;

public class ExecutionSerializationException extends RuntimeException {

    public ExecutionSerializationException(String message) {
        super(message);
    }

    public ExecutionSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
