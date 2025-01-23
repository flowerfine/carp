package cn.sliew.carp.module.orca.spinnaker.api.persistence;

public class UnresumablePipelineException extends IllegalStateException {

    public UnresumablePipelineException(String s) {
        super(s);
    }

    public UnresumablePipelineException(String message, Throwable cause) {
        super(message, cause);
    }
}
