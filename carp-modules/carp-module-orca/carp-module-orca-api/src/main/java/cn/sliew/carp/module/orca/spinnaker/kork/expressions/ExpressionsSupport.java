package cn.sliew.carp.module.orca.spinnaker.kork.expressions;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist.*;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.config.ExpressionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.String.format;

/**
 * Provides utility support for SpEL integration Supports registering SpEL functions, ACLs to
 * classes (via allow list)
 */
@Slf4j
public class ExpressionsSupport {

    private final Set<Class<?>> allowedReturnTypes;
    private final List<ExpressionFunctionProvider> expressionFunctionProviders;
    private final ExpressionProperties expressionProperties;

    public ExpressionsSupport(
            Class<?> extraAllowedReturnType, ExpressionProperties expressionProperties) {
        this(new Class[]{extraAllowedReturnType}, null, expressionProperties);
    }

    public ExpressionsSupport(
            Class<?>[] extraAllowedReturnTypes,
            List<ExpressionFunctionProvider> extraExpressionFunctionProviders,
            ExpressionProperties expressionProperties) {
        this.expressionProperties = expressionProperties;

        allowedReturnTypes =
                new HashSet<>(
                        Arrays.asList(
                                Collection.class,
                                Map.class,
                                SortedMap.class,
                                List.class,
                                Set.class,
                                SortedSet.class,
                                ArrayList.class,
                                LinkedList.class,
                                HashSet.class,
                                LinkedHashSet.class,
                                HashMap.class,
                                LinkedHashMap.class,
                                TreeMap.class,
                                TreeSet.class));
        Collections.addAll(allowedReturnTypes, extraAllowedReturnTypes);

        expressionFunctionProviders =
                new ArrayList<>(
                        Arrays.asList(
                                new JsonExpressionFunctionProvider(),
                                new StringExpressionFunctionProvider()));

        if (extraExpressionFunctionProviders != null) {
            expressionFunctionProviders.addAll(extraExpressionFunctionProviders);
        }

        if (expressionProperties.getDoNotEvalSpel().isEnabled()) {
            allowedReturnTypes.add(NotEvaluableExpression.class);
            expressionFunctionProviders.add(new FlowExpressionFunctionProvider());
        }
    }

    public List<ExpressionFunctionProvider> getExpressionFunctionProviders() {
        return expressionFunctionProviders;
    }

    private static void registerFunction(
            StandardEvaluationContext context,
            String registrationName,
            Class<?> cls,
            String methodName,
            Class<?>... types) {
        try {
            context.registerFunction(registrationName, cls.getDeclaredMethod(methodName, types));
        } catch (NoSuchMethodException e) {
            log.error("Failed to register helper function", e);
            throw new RuntimeException(
                    "Failed to register helper function '"
                            + registrationName
                            + "' from '"
                            + cls.getName()
                            + "#"
                            + methodName
                            + "'",
                    e);
        }
    }

    /**
     * Creates a configured SpEL evaluation context
     *
     * @param rootObject       the root object to transform
     * @param allowUnknownKeys flag to control what helper functions are available
     * @return an evaluation context hooked with helper functions and correct ACL via allow list
     */
    public StandardEvaluationContext buildEvaluationContext(
            Object rootObject, boolean allowUnknownKeys) {
        StandardEvaluationContext evaluationContext =
                createEvaluationContext(rootObject, allowUnknownKeys);

        registerExpressionProviderFunctions(evaluationContext);

        return evaluationContext;
    }

    private StandardEvaluationContext createEvaluationContext(
            Object rootObject, boolean allowUnknownKeys) {
        ReturnTypeRestrictor returnTypeRestrictor = new ReturnTypeRestrictor(allowedReturnTypes);

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(rootObject);
        evaluationContext.setTypeLocator(new AllowListTypeLocator());

        evaluationContext.setMethodResolvers(
                Collections.singletonList(new FilteredMethodResolver(returnTypeRestrictor)));
        evaluationContext.setPropertyAccessors(
                Arrays.asList(
                        new MapPropertyAccessor(allowUnknownKeys),
                        new FilteredPropertyAccessor(returnTypeRestrictor)));

        return evaluationContext;
    }

    private void registerExpressionProviderFunctions(StandardEvaluationContext evaluationContext) {
        for (ExpressionFunctionProvider p : expressionFunctionProviders) {
            for (ExpressionFunctionProvider.FunctionDefinition function :
                    p.getFunctions().getFunctionsDefinitions()) {
                String namespacedFunctionName = function.getName();
                if (p.getNamespace() != null) {
                    namespacedFunctionName = format("%s_%s", p.getNamespace(), namespacedFunctionName);
                }

                Class[] functionTypes =
                        function.getParameters().stream()
                                .map(ExpressionFunctionProvider.FunctionParameter::getType)
                                .toArray(Class[]::new);

                registerFunction(
                        evaluationContext,
                        namespacedFunctionName,
                        p.getExtensionClass(),
                        function.getName(),
                        functionTypes);
            }
        }
    }

    @SuppressWarnings("unused")
    public static class FlowExpressionFunctionProvider implements ExpressionFunctionProvider {
        /**
         * @param o represents an object to restrict expressions evaluation
         * @return not evaluable expression
         */
        public static NotEvaluableExpression doNotEval(Object o) {
            return new NotEvaluableExpression(o);
        }

        @Override
        public String getNamespace() {
            return null;
        }

        @Override
        public Functions getFunctions() {
            return new Functions(
                    new FunctionDefinition(
                            "doNotEval",
                            "Restrict expressions evaluation for an object",
                            new FunctionParameter(
                                    Object.class, "value", "An object to restrict expressions evaluation")));
        }
    }

    @SuppressWarnings("unused")
    public static class JsonExpressionFunctionProvider implements ExpressionFunctionProvider {
        /**
         * @param o represents an object to convert to json
         * @return json representation of the said object
         */
        public static String toJson(Object o) {
            try {
                if (o instanceof NotEvaluableExpression expression) {
                    return JacksonUtil.toJsonString(expression.getExpression());
                }

                String converted = JacksonUtil.toJsonString(o);
                if (converted != null && converted.contains("${")) {
                    throw new SpelHelperFunctionException("result for toJson cannot contain an expression");
                }

                return converted;
            } catch (Exception e) {
                throw new SpelHelperFunctionException(format("#toJson(%s) failed", o.toString()), e);
            }
        }

        @Override
        public String getNamespace() {
            return null;
        }

        @Override
        public Functions getFunctions() {
            return new Functions(
                    new FunctionDefinition(
                            "toJson",
                            "Converts an object to JSON string",
                            new FunctionParameter(
                                    Object.class, "value", "An Object to marshall to a JSON String")));
        }
    }

    @SuppressWarnings("unused")
    public static class StringExpressionFunctionProvider implements ExpressionFunctionProvider {
        /**
         * Parses a string to an integer
         *
         * @param str represents an int
         * @return an integer
         */
        public static Integer toInt(String str) {
            return Integer.valueOf(str);
        }

        /**
         * Parses a string to a float
         *
         * @param str represents an float
         * @return an float
         */
        public static Float toFloat(String str) {
            return Float.valueOf(str);
        }

        /**
         * Parses a string to a boolean
         *
         * @param str represents an boolean
         * @return a boolean
         */
        public static Boolean toBoolean(String str) {
            return Boolean.valueOf(str);
        }

        /**
         * Encodes a string to base64
         *
         * @param text plain string
         * @return converted string
         */
        public static String toBase64(String text) {
            return Base64.getEncoder().encodeToString(text.getBytes());
        }

        /**
         * Attempts to decode a base64 string
         *
         * @param text plain string
         * @return decoded string
         */
        public static String fromBase64(String text) {
            return new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8);
        }

        /**
         * Converts a String to alpha numeric
         *
         * @param str string to convert
         * @return converted string
         */
        public static String alphanumerical(String str) {
            return str.replaceAll("[^A-Za-z0-9]", "");
        }

        @Override
        public String getNamespace() {
            return null;
        }

        @Override
        public Functions getFunctions() {
            return new Functions(
                    new FunctionDefinition(
                            "toInt",
                            "Converts a string to integer",
                            new FunctionParameter(String.class, "value", "A String value to convert to an int")),
                    new FunctionDefinition(
                            "toFloat",
                            "Converts a string to float",
                            new FunctionParameter(String.class, "value", "A String value to convert to a float")),
                    new FunctionDefinition(
                            "toBoolean",
                            "Converts a string value to boolean",
                            new FunctionParameter(
                                    String.class, "value", "A String value to convert to a boolean")),
                    new FunctionDefinition(
                            "toBase64",
                            "Encodes a string to base64 string",
                            new FunctionParameter(String.class, "value", "A String value to base64 encode")),
                    new FunctionDefinition(
                            "fromBase64",
                            "Decodes a base64 string",
                            new FunctionParameter(
                                    String.class, "value", "A base64-encoded String value to decode")),
                    new FunctionDefinition(
                            "alphanumerical",
                            "Removes all non-alphanumeric characters from a string",
                            new FunctionParameter(
                                    String.class,
                                    "value",
                                    "A String value to strip of all non-alphanumeric characters")));
        }
    }
}
