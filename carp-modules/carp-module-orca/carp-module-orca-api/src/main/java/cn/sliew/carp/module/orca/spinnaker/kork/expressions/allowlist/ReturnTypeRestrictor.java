package cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist;

import java.util.Set;

public class ReturnTypeRestrictor extends InstantiationTypeRestrictor {
    private final Set<Class<?>> allowedReturnTypes;

    public ReturnTypeRestrictor(Set<Class<?>> allowedReturnTypes) {
        this.allowedReturnTypes = allowedReturnTypes;
    }

    @Override
    boolean supports(Class<?> type) {
        Class<?> returnType = type.isArray() ? type.getComponentType() : type;
        return returnType.isPrimitive()
                || super.supports(returnType)
                || allowedReturnTypes.contains(returnType);
    }
}
