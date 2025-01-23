package cn.sliew.carp.module.orca.config;

import cn.sliew.carp.module.orca.spinnaker.kork.expressions.config.ExpressionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        ExpressionProperties.class
})
public class SpinnakerKorkConfig {
}
