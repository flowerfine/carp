package cn.sliew.carp.example.redisson.service;

import org.redisson.api.RIdGenerator;
import org.redisson.api.RReliableTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubService implements InitializingBean {

    public static final String RELIABLE_TOPIC_NAME = "reliableTopic";

    @Autowired
    private RedissonClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        RReliableTopic reliableTopic = client.getReliableTopic(RELIABLE_TOPIC_NAME);
    }
}
