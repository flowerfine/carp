package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class ArrayConditionHandler implements ConditionHandler {

    @Override
    public boolean handle(ConditionValue condition) {
        switch (condition.getOperator()) {
            case IS_EMPTY:
                return Objects.isNull(condition.getLeftValue()) || CollectionUtils.isEmpty((List)condition.getLeftValue());
            case IS_NOT_EMPTY:
                return !(Objects.isNull(condition.getLeftValue()) || CollectionUtils.isEmpty((List)condition.getLeftValue()));
            default:
                return false;
        }
    }
}
