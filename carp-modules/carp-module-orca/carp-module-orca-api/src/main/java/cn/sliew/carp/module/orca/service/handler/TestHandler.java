package cn.sliew.carp.module.orca.service.handler;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.carp.module.orca.config.SpinnakerKeikoConfig;
import cn.sliew.carp.module.orca.service.message.TestMessage;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.MessageHandler;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TestHandler implements MessageHandler<TestMessage> {

    @Qualifier(SpinnakerKeikoConfig.IN_MEMORY_QUEUE)
    private Queue queue;

    @Override
    public Class<TestMessage> getMessageType() {
        return TestMessage.class;
    }

    @Override
    public Queue getQueue() {
        return queue;
    }

    @Override
    public void handle(TestMessage message) {
        log.info("receive message: {}", JacksonUtil.toJsonString(message));
    }
}
