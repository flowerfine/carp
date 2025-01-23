package cn.sliew.carp.module.orca.spinnaker.kork.expressions.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Spring Expression Language (SpEL) evaluation related features.
 */
@Data
@ConfigurationProperties(prefix = "expression")
public class ExpressionProperties {

    /**
     * Flag to determine if SpEL evaluation to be skipped.
     */
    private final FeatureFlag doNotEvalSpel = new FeatureFlag().setEnabled(true);

    /**
     * To set the maximum limit of characters in expression for SpEL evaluation. Default value -1
     * signifies to use default maximum limit of 10,000 characters provided by springframework.
     */
    private int maxExpressionLength = -1;

    @Data
    @Accessors(chain = true)
    public static class FeatureFlag {
        private boolean enabled;
    }
}
