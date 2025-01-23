package cn.sliew.carp.module.orca.spinnaker.orca.core.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.Callable;

public interface RunOnLockAcquired {

    /**
     * Executes an action after lock identified by {@code keyName} was obtained
     *
     * @param action  action to execute once lock is acquired
     * @param keyName name of a lock
     * @return result of attempting to acquire a lock
     */
    RunOnLockResult<Void> execute(Runnable action, String keyName);

    /**
     * Executes an action after lock identified by {@code keyName} was obtained
     *
     * @param action  action to execute once lock is acquired
     * @param keyName name of a lock
     * @return result of attempting to acquire a lock and result of action execution
     */
    <R> RunOnLockResult<R> execute(Callable<R> action, String keyName);

    @Slf4j
    public class RunOnShedLockAcquired implements RunOnLockAcquired {
        private final LockProvider shedLockProvider;

        public RunOnShedLockAcquired(LockProvider shedLockProvider) {
            this.shedLockProvider = shedLockProvider;
        }

        @Override
        public RunOnLockResult<Void> execute(Runnable action, String keyName) {
            Optional<SimpleLock> lockOpt = this.getLock(keyName);
            if (!lockOpt.isPresent()) {
                log.error("Failed to acquire shedlock for key: {}", keyName);
                return new RunOnLockResult<>(false);
            }

            try {
                log.debug("Executing action with a lock for key: {}", keyName);
                action.run();
                log.debug("Finished action execution with a lock for key: {}", keyName);
                return new RunOnLockResult<>(true, true);
            } catch (Exception e) {
                log.error("An exception occurred while executing action with a lock for key: {}", keyName);
                return new RunOnLockResult<>(true, e);
            } finally {
                lockOpt.get().unlock();
                log.debug("Released shedlock for key {}", keyName);
            }
        }

        @Override
        public <R> RunOnLockResult<R> execute(Callable<R> action, String keyName) {
            Optional<SimpleLock> lockOpt = this.getLock(keyName);
            if (!lockOpt.isPresent()) {
                log.error("Failed to acquire shedlock for key: {}", keyName);
                return new RunOnLockResult<>(false);
            }

            try {
                log.debug("Executing action with a lock for key: {}", keyName);
                R result = action.call();
                log.debug("Finished action execution with a lock for key: {}", keyName);
                return new RunOnLockResult<>(true, true, result);
            } catch (Exception e) {
                log.error("An exception occurred while executing action with a lock for key: {}", keyName);
                return new RunOnLockResult<>(true, e);
            } finally {
                lockOpt.get().unlock();
                log.debug("Released shedlock for key {}", keyName);
            }
        }

        private Optional<SimpleLock> getLock(String keyName) {
            try {
                log.debug("Attempt to acquire shedlock for key: {}", keyName);
                return shedLockProvider.lock(
                        new LockConfiguration(
                                Instant.now(),
                                keyName,
                                Duration.ofSeconds(1),
                                Duration.ofMillis(200)
                        )
                );
            } catch (Exception e) {
                log.error("An exception occurred during an attempt to acquire shedlock for key: {}", keyName);
                log.error(e.getMessage());
                throw e;
            }
        }
    }

    public class RunOnRedisLockAcquired implements RunOnLockAcquired {
        private final LockManager lockManager;

        public RunOnRedisLockAcquired(LockManager lockManager) {
            this.lockManager = lockManager;
        }

        private LockManager.LockOptions lockOptions(String name) {
            return new LockManager.LockOptions()
                    .withLockName(name)
                    .withMaximumLockDuration(Duration.ofSeconds(1L));
        }

        @Override
        public RunOnLockResult<Void> execute(Runnable action, String keyName) {
            try {
                LockManager.AcquireLockResponse acquireLock = lockManager.acquireLock(lockOptions(keyName), action);
                if (!acquireLock.getLockStatus().equals(LockManager.LockStatus.ACQUIRED)) {
                    return new RunOnLockResult<>(false);
                }
                return new RunOnLockResult(true, true, null, acquireLock.getOnLockAcquiredCallbackResult());
            } catch (Exception e) {
                return new RunOnLockResult<>(true, e);
            }
        }

        @Override
        public <R> RunOnLockResult<R> execute(Callable<R> action, String keyName) {
            try {
                LockManager.AcquireLockResponse acquireLock = lockManager.acquireLock(lockOptions(keyName), action);
                if (!acquireLock.getLockStatus().equals(LockManager.LockStatus.ACQUIRED)) {
                    return new RunOnLockResult<>(false);
                }
                return new RunOnLockResult(true, true, acquireLock.getOnLockAcquiredCallbackResult());
            } catch (Exception e) {
                return new RunOnLockResult<>(true, e);
            }
        }
    }

    @Slf4j
    public class NoOpRunOnLockAcquired implements RunOnLockAcquired {

        @Override
        public RunOnLockResult<Void> execute(Runnable action, String keyName) {
            try {
                log.debug("Executing action with no locking for key: {}", keyName);
                action.run();
                log.debug("Execution with no locking for key: {} successful", keyName);
                return new RunOnLockResult<>(true, true);
            } catch (Exception e) {
                log.error("An exception was thrown while executing action with no locking for key: {}", keyName);
                log.error(e.getMessage());
                return new RunOnLockResult<>(false, e);
            }
        }

        @Override
        public <R> RunOnLockResult<R> execute(Callable<R> action, String keyName) {
            try {
                return new RunOnLockResult<>(true, true, action.call());
            } catch (Exception e) {
                return new RunOnLockResult<>(false, e);
            }
        }
    }

    @Getter
    @AllArgsConstructor
    public class RunOnLockResult<R> {
        private final boolean lockAcquired;
        private final boolean actionExecuted;
        private final Exception exception;
        private final R result;

        public RunOnLockResult(boolean lockAcquired) {
            this(lockAcquired, false, null, null);
        }

        public RunOnLockResult(boolean lockAcquired, boolean actionExecuted) {
            this(lockAcquired, actionExecuted, null, null);
        }

        public RunOnLockResult(boolean lockAcquired, Exception exception) {
            this(lockAcquired, false, exception, null);
        }

        public RunOnLockResult(boolean lockAcquired, boolean actionExecuted, R result) {
            this(lockAcquired, actionExecuted, null, result);
        }
    }
}
