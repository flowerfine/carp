package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;

import java.util.HashMap;
import java.util.Map;

public enum ConditionHandlers {
    ;

    public static final Map<WorkflowVariableType, ConditionHandler> INSTANCE = new HashMap<>();

    static {
        INSTANCE.put(WorkflowVariableType.STRING, new StringConditionHandler());
        INSTANCE.put(WorkflowVariableType.NUMBER, new NumberConditionHandler());
        INSTANCE.put(WorkflowVariableType.INTEGER, new NumberConditionHandler());
        INSTANCE.put(WorkflowVariableType.BOOLEAN, new BooleanConditionHandler());
        INSTANCE.put(WorkflowVariableType.OBJECT, new ObjectConditionHandler());
        INSTANCE.put(WorkflowVariableType.MAP, new MapConditionHandler());
        INSTANCE.put(WorkflowVariableType.ARRAY, new ArrayConditionHandler());
        INSTANCE.put(WorkflowVariableType.DATE_TIME, new DateTimeConditionHandler());
        INSTANCE.put(WorkflowVariableType.NULL, new NullConditionHandler());
    }
}
