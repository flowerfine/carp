package cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist;

import org.springframework.expression.spel.support.ReflectiveMethodResolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class FilteredMethodResolver extends ReflectiveMethodResolver {
    private static final List<Method> rejectedMethods = buildRejectedMethods();
    private final ReturnTypeRestrictor returnTypeRestrictor;

    public FilteredMethodResolver(ReturnTypeRestrictor returnTypeRestrictor) {
        this.returnTypeRestrictor = returnTypeRestrictor;
    }

    private static List<Method> buildRejectedMethods() {
        try {
            List<Method> allowedObjectMethods =
                    asList(
                            Object.class.getMethod("equals", Object.class),
                            Object.class.getMethod("hashCode"),
                            Object.class.getMethod("toString"));
            return Stream.concat(
                            stream(Object.class.getMethods()).filter(it -> !allowedObjectMethods.contains(it)),
                            Stream.concat(
                                    stream(Boolean.class.getMethods())
                                            .filter(it -> it.getName().equals("getBoolean")),
                                    Stream.concat(
                                            stream(Integer.class.getMethods())
                                                    .filter(it -> it.getName().equals("getInteger")),
                                            Stream.concat(
                                                    stream(Long.class.getMethods())
                                                            .filter(it -> it.getName().equals("getLong")),
                                                    stream(Class.class.getMethods())))))
                    .collect(toList());
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected Method[] getMethods(Class<?> type) {
        Method[] methods = super.getMethods(type);

        List<Method> m = new ArrayList<>(asList(methods));
        m.removeAll(rejectedMethods);
        m =
                m.stream()
                        .filter(it -> returnTypeRestrictor.supports(it.getReturnType()))
                        .collect(toList());

        return m.toArray(new Method[0]);
    }
}
