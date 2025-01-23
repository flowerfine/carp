package cn.sliew.carp.module.orca.spinnaker.kork.expressions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * An instance of this class is used as the return value from the {@code doNotEval} SpEL helper
 * which makes it possible to skip SpEL evaluation in other SpEL helpers e.g. {@code toJson}.
 *
 * <p>For example, in the evaluation context is defined only {@code fileMap} object:
 *
 * <pre>{@code
 * Map<String, Object> fileMap = Collections.singletonMap("owner", "managed-by-${team}");
 * }</pre>
 *
 * <p>An exception will be thrown in attempt to get JSON because of {@code fileMap} contains SpEL
 * inside.
 *
 * <pre>{@code
 * ${#toJson(fileMap)}
 * }</pre>
 *
 * <p>In the given case {@code fileMap} contains SpEL for another tool e.g. Terraform. Use {@code
 * doNotEval} to let Spinnaker know that this SpEL should be evaluated by a different tool. No
 * exceptions are thrown this way.
 *
 * <pre>{@code
 * ${#toJson(#doNotEval(fileMap))}
 * }</pre>
 */
@Data
@AllArgsConstructor
public class NotEvaluableExpression {

    private final Object expression;
}
