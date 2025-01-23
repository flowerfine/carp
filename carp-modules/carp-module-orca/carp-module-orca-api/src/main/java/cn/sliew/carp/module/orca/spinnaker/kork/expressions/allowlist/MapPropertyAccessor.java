package cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;

public class MapPropertyAccessor extends MapAccessor {
    private final boolean allowUnknownKeys;

    public MapPropertyAccessor(boolean allowUnknownKeys) {
        this.allowUnknownKeys = allowUnknownKeys;
    }

    @Override
    public boolean canRead(final EvaluationContext context, final Object target, final String name)
            throws AccessException {
        if (allowUnknownKeys) {
            return true;
        }
        return super.canRead(context, target, name);
    }

    @Override
    public TypedValue read(final EvaluationContext context, final Object target, final String name)
            throws AccessException {
        try {
            return super.read(context, target, name);
        } catch (AccessException ae) {
            if (allowUnknownKeys) {
                return TypedValue.NULL;
            }
            throw ae;
        }
    }
}
