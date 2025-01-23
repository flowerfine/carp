package cn.sliew.carp.module.orca.spinnaker.api.persistence;

public class UnsupportedRepositoryException extends RuntimeException {

    public UnsupportedRepositoryException(String message) {
        super(message);
    }

    public UnsupportedRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
