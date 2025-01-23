package cn.sliew.carp.module.orca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SpinnakerConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
