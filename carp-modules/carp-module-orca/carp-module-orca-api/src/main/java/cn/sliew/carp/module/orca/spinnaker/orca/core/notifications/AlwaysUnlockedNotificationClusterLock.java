package cn.sliew.carp.module.orca.spinnaker.orca.core.notifications;

public class AlwaysUnlockedNotificationClusterLock implements NotificationClusterLock {

    @Override
    public boolean tryAcquireLock(String notificationType, Long lockTimeoutSeconds) {
        return true;
    }
}
