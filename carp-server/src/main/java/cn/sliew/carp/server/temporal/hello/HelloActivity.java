package cn.sliew.carp.server.temporal.hello;

import io.temporal.activity.ActivityInterface;

@ActivityInterface()
public interface HelloActivity {

    String hello();
}
