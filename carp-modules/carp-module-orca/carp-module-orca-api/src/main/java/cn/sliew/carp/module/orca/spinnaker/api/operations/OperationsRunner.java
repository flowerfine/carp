package cn.sliew.carp.module.orca.spinnaker.api.operations;

/**
 * An operations runner will run one or more operation and return the resulting context.
 */
public interface OperationsRunner {

    /**
     * Run one or more operations.
     */
    OperationsContext run(OperationsInput operationsInput);
}
