package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;

public interface ConditionHandler {

    boolean handle(ConditionValue condition);
}
