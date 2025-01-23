package cn.sliew.carp.module.orca.spinnaker.api.persistence;

public class ExecutionNotFoundException extends RuntimeException {

    public ExecutionNotFoundException(String message) {
        super(message);
    }
}
