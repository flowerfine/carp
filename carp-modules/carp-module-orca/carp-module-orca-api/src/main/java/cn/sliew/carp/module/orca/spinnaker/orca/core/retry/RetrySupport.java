package cn.sliew.carp.module.orca.spinnaker.orca.core.retry;

import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.framework.exception.SliewException;

import java.time.Duration;
import java.util.function.Supplier;

public class RetrySupport {

    public <T> T retry(Supplier<T> fn, int maxRetries, Duration retryBackoff, boolean exponential) {
        int retries = 0;
        while (true) {
            try {
                return fn.get();
            } catch (Exception e) {
                if (e instanceof SliewException) {
                    Boolean retryable = ((SliewException) e).getRetryable();
                    if (retryable != null && !retryable) {
                        throw e;
                    }
                }
                if (retries >= (maxRetries - 1)) {
                    throw e;
                }

                long timeout =
                        !exponential
                                ? retryBackoff.toMillis()
                                : (long) Math.pow(2, retries) * retryBackoff.toMillis();
                ThreadUtil.sleep(timeout);

                retries++;
            }
        }
    }
}
