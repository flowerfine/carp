package cn.sliew.carp.module.orca.spinnaker.api.persistence;

public class UnpausablePipelineException extends IllegalStateException {

    public UnpausablePipelineException(String s) {
        super(s);
    }

    public UnpausablePipelineException(String message, Throwable cause) {
        super(message, cause);
    }
}
