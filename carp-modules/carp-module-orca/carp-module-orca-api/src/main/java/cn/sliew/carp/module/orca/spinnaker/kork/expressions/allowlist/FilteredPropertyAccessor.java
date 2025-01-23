package cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist;

import org.springframework.expression.spel.support.ReflectivePropertyAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static java.lang.String.format;

public class FilteredPropertyAccessor extends ReflectivePropertyAccessor {
    private final ReturnTypeRestrictor returnTypeRestrictor;

    public FilteredPropertyAccessor(ReturnTypeRestrictor returnTypeRestrictor) {
        this.returnTypeRestrictor = returnTypeRestrictor;
    }

    @Override
    protected Method findGetterForProperty(
            String propertyName, Class<?> clazz, boolean mustBeStatic) {
        Method getter = super.findGetterForProperty(propertyName, clazz, mustBeStatic);
        if (getter == null) {
            throw new IllegalArgumentException(
                    format("requested getter %s not found on type %s", propertyName, clazz));
        } else if (!returnTypeRestrictor.supports(getter.getReturnType())) {
            throw new IllegalArgumentException(
                    format(
                            "found getter for requested %s but rejected due to return type %s",
                            propertyName, getter.getReturnType()));
        }
        return getter;
    }

    @Override
    protected Field findField(String name, Class<?> clazz, boolean mustBeStatic) {
        Field field = super.findField(name, clazz, mustBeStatic);
        if (field == null) {
            throw new IllegalArgumentException(
                    format("requested field %s not found on type %s", name, clazz));
        } else if (!returnTypeRestrictor.supports(field.getType())) {
            throw new IllegalArgumentException(
                    format("found field %s but rejected due to unsupported type %s", name, clazz));
        }
        return field;
    }
}
