package cn.sliew.carp.module.orca.config;

import cn.sliew.carp.module.orca.spinnaker.orca.core.lock.RetriableLock;
import cn.sliew.carp.module.orca.spinnaker.orca.core.lock.RunOnLockAcquired;
import cn.sliew.carp.module.orca.spinnaker.orca.core.retry.RetrySupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

    @Bean
    public RetrySupport retrySupport() {
        return new RetrySupport();
    }

    @Bean
    @ConditionalOnMissingBean(RunOnLockAcquired.class)
    public RunOnLockAcquired noOpLocking() {
        return new RunOnLockAcquired.NoOpRunOnLockAcquired();
    }

    @Bean
    public RetriableLock retriableLock(
            RunOnLockAcquired runOnLockAcquired,
            RetrySupport retrySupport) {
        return new RetriableLock(runOnLockAcquired, retrySupport);
    }
}
