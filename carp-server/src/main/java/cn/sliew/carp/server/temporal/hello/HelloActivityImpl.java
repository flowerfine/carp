package cn.sliew.carp.server.temporal.hello;

import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = HelloWorkflow.WORKFLOW_TASK_QUEUE)
public class HelloActivityImpl implements HelloActivity {

    @Value("${hello.word:world}")
    private String word;

    @Override
    public String hello() {
        return "hello, " + word;
    }
}
