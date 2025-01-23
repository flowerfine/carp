package cn.sliew.carp.module.orca.spinnaker.api.model.task;

/**
 * A retryable task whose timeout is taken from the top level stage if that value has been
 * overridden. {@see com.netflix.spinnaker.orca.q.handler.RunTaskHandler} for the way this interface
 * is used.
 *
 * <p>These are typically wait/monitor stages
 */
public interface OverridableTimeoutRetryableTask extends RetryableTask {
}
