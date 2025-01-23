package cn.sliew.carp.module.orca.service;

import cn.sliew.carp.module.orca.service.message.TestMessage;

public interface QueueService {

    void push(TestMessage message);
}
