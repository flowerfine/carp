package cn.sliew.carp.example.camellia.service;

import cn.sliew.carp.example.camellia.listener.ConsumerService1;
import cn.sliew.carp.framework.common.reflection.JobDetails;
import cn.sliew.carp.framework.common.reflection.JobDetailsAsmGenerator;
import cn.sliew.carp.framework.common.reflection.JobDetailsGenerator;
import cn.sliew.carp.framework.common.reflection.lambdas.JobLambda;
import cn.sliew.milky.common.util.JacksonUtil;
import com.netease.nim.camellia.delayqueue.common.domain.CamelliaDelayMsg;
import com.netease.nim.camellia.delayqueue.sdk.CamelliaDelayQueueSdk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DelayMsgService {

    @Autowired(required = false)
    private CamelliaDelayQueueSdk delayQueueSdk;

    public CamelliaDelayMsg sendSimpleDelayMsg(String msg, long delaySeconds, long ttlSeconds, int maxRetry) {
        String topic = ConsumerService1.TOPIC;
        log.info("sendSimpleDelayMsg, topic = {}, msg = {}, delaySeconds = {}, ttlSeconds = {}, maxRetry = {}", topic, msg, delaySeconds, ttlSeconds, maxRetry);
        return delayQueueSdk.sendMsg(topic, msg, delaySeconds, TimeUnit.SECONDS, ttlSeconds, TimeUnit.SECONDS, maxRetry);
    }

    public CamelliaDelayMsg sendLambdaDelayMsg(String msg, long delaySeconds, long ttlSeconds, int maxRetry) {
        return doSendLambdaDelayMsg(() -> System.out.println(msg), delaySeconds, ttlSeconds, maxRetry);
    }

    public CamelliaDelayMsg doSendLambdaDelayMsg(JobLambda jobLambda, long delaySeconds, long ttlSeconds, int maxRetry) {
        JobDetailsGenerator generator = new JobDetailsAsmGenerator();
        JobDetails jobDetails = generator.toJobDetails(jobLambda);
        String msg = JacksonUtil.toJsonString(jobDetails);
        String topic = ConsumerService1.TOPIC;
        log.info("sendDelayMsg, topic = {}, msg = {}, delaySeconds = {}, ttlSeconds = {}, maxRetry = {}", topic, msg, delaySeconds, ttlSeconds, maxRetry);
        return delayQueueSdk.sendMsg(topic, msg, delaySeconds, TimeUnit.SECONDS, ttlSeconds, TimeUnit.SECONDS, maxRetry);
    }


}
