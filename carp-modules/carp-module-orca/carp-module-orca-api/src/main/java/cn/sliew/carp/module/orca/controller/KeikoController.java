package cn.sliew.carp.module.orca.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.module.orca.service.QueueService;
import cn.sliew.carp.module.orca.service.message.TestMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AnonymousAccess
@RestController
@RequestMapping("/api/carp/orca/keiko")
@Tag(name = "队列模块-Keiko测试")
public class KeikoController {

    @Autowired
    private QueueService queueService;

    @PutMapping("/push")
    @Operation(summary = "推送消息", description = "推送消息")
    public void push() {
        TestMessage message = new TestMessage();
        message.setName("test message push");
        queueService.push(message);
    }
}
