package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;

import java.util.Objects;

public class NullConditionHandler implements ConditionHandler {

    @Override
    public boolean handle(ConditionValue condition) {
        switch (condition.getOperator()) {
            case EQ:
                return Objects.isNull(condition.getLeftValue()) && Objects.isNull(condition.getRightValue());
            case IS_EMPTY:
                return Objects.isNull(condition.getLeftValue());
            case IS_NOT_EMPTY:
                return Objects.nonNull(condition.getLeftValue());
            default:
                return false;
        }
    }
}
