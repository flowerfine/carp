package cn.sliew.carp.module.orca.service.impl;

import cn.sliew.carp.module.orca.config.SpinnakerKeikoConfig;
import cn.sliew.carp.module.orca.service.QueueService;
import cn.sliew.carp.module.orca.service.message.TestMessage;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueService {

    @Autowired
    @Qualifier(SpinnakerKeikoConfig.IN_MEMORY_QUEUE)
    private Queue queue;

    @Override
    public void push(TestMessage message) {
        queue.push(message);
    }
}
