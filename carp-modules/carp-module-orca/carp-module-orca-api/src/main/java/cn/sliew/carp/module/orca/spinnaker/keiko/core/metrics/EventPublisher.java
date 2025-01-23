package cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics;

public interface EventPublisher {

    void publishEvent(QueueEvent event);

    class NoopEventPublisher implements EventPublisher {

        @Override
        public void publishEvent(QueueEvent event) {

        }
    }
}
