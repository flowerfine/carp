package cn.sliew.carp.module.queue.redis.test1;

public class TestMessage {

    private Long ackTimeoutMs;

    private Integer maxAttempts = -1;
    private Integer attempts = 0;
    private Integer ackAttempts = 0;
}
