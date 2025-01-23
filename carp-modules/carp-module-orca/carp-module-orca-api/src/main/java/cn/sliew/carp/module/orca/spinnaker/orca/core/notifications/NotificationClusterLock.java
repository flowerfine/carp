package cn.sliew.carp.module.orca.spinnaker.orca.core.notifications;

public interface NotificationClusterLock {

    boolean tryAcquireLock(String notificationType, Long lockTimeoutSeconds);
}
