package cn.sliew.carp.module.orca.spinnaker.api.persistence;

public class StageSerializationException extends RuntimeException {

    public StageSerializationException(String message) {
        super(message);
    }

    public StageSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
