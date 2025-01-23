package cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;
import org.springframework.expression.spel.support.StandardTypeLocator;

public class AllowListTypeLocator implements TypeLocator {
    private final InstantiationTypeRestrictor instantiationTypeRestrictor =
            new InstantiationTypeRestrictor();
    private final TypeLocator delegate = new StandardTypeLocator();

    @Override
    public Class<?> findType(String typeName) throws EvaluationException {
        Class<?> type = delegate.findType(typeName);
        if (instantiationTypeRestrictor.supports(type)) {
            return type;
        }
        throw new SpelEvaluationException(SpelMessage.EXCEPTION_DURING_PROPERTY_READ, typeName);
    }
}
