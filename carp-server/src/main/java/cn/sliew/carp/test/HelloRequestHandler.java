package cn.sliew.carp.test;

import cn.sliew.carp.support.annotation.processor.plugins.web.HttpMethod;
import cn.sliew.carp.support.annotation.processor.plugins.web.RequestHandle;

public class HelloRequestHandler {

    @RequestHandle(method = HttpMethod.GET)
    public String hello(String name) {
        return "hello, %s".formatted(name);
    }
}
