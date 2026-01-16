package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.Objects;

public class MapConditionHandler implements ConditionHandler {

    @Override
    public boolean handle(ConditionValue condition) {
        switch (condition.getOperator()) {
            case IS_EMPTY:
                return Objects.isNull(condition.getLeftValue()) || MapUtils.isEmpty((Map)condition.getLeftValue());
            case IS_NOT_EMPTY:
                return !(Objects.isNull(condition.getLeftValue()) || MapUtils.isEmpty((Map)condition.getLeftValue()));
            default:
                return false;
        }
    }
}
