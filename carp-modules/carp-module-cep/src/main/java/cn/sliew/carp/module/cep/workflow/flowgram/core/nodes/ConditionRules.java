package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.ConditionOperator;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;

import java.util.HashMap;
import java.util.Map;

public enum ConditionRules {
    ;

    public static final Map<WorkflowVariableType, Map<ConditionOperator, WorkflowVariableType>> INSTNACE = new HashMap<>();

    static {
        Map<ConditionOperator, WorkflowVariableType> STRING_OPERATOR = new HashMap<>();
        STRING_OPERATOR.put(ConditionOperator.EQ, WorkflowVariableType.STRING);
        STRING_OPERATOR.put(ConditionOperator.NEQ, WorkflowVariableType.STRING);
        STRING_OPERATOR.put(ConditionOperator.CONTAINS, WorkflowVariableType.STRING);
        STRING_OPERATOR.put(ConditionOperator.NOT_CONTAINS, WorkflowVariableType.STRING);
        STRING_OPERATOR.put(ConditionOperator.IN, WorkflowVariableType.ARRAY);
        STRING_OPERATOR.put(ConditionOperator.NIN, WorkflowVariableType.ARRAY);
        STRING_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.STRING);
        STRING_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.STRING);
        INSTNACE.put(WorkflowVariableType.STRING, STRING_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> NUMBER_OPERATOR = new HashMap<>();
        NUMBER_OPERATOR.put(ConditionOperator.EQ, WorkflowVariableType.NUMBER);
        NUMBER_OPERATOR.put(ConditionOperator.NEQ, WorkflowVariableType.NUMBER);
        NUMBER_OPERATOR.put(ConditionOperator.GT, WorkflowVariableType.NUMBER);
        NUMBER_OPERATOR.put(ConditionOperator.GTE, WorkflowVariableType.NUMBER);
        NUMBER_OPERATOR.put(ConditionOperator.LT, WorkflowVariableType.NUMBER);
        NUMBER_OPERATOR.put(ConditionOperator.LTE, WorkflowVariableType.NUMBER);
        NUMBER_OPERATOR.put(ConditionOperator.IN, WorkflowVariableType.ARRAY);
        NUMBER_OPERATOR.put(ConditionOperator.NIN, WorkflowVariableType.ARRAY);
        NUMBER_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        NUMBER_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.NUMBER, NUMBER_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> INTEGER_OPERATOR = new HashMap<>();
        INTEGER_OPERATOR.put(ConditionOperator.EQ, WorkflowVariableType.INTEGER);
        INTEGER_OPERATOR.put(ConditionOperator.NEQ, WorkflowVariableType.INTEGER);
        INTEGER_OPERATOR.put(ConditionOperator.GT, WorkflowVariableType.INTEGER);
        INTEGER_OPERATOR.put(ConditionOperator.GTE, WorkflowVariableType.INTEGER);
        INTEGER_OPERATOR.put(ConditionOperator.LT, WorkflowVariableType.INTEGER);
        INTEGER_OPERATOR.put(ConditionOperator.LTE, WorkflowVariableType.INTEGER);
        INTEGER_OPERATOR.put(ConditionOperator.IN, WorkflowVariableType.ARRAY);
        INTEGER_OPERATOR.put(ConditionOperator.NIN, WorkflowVariableType.ARRAY);
        INTEGER_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        INTEGER_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.INTEGER, INTEGER_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> BOOLEAN_OPERATOR = new HashMap<>();
        BOOLEAN_OPERATOR.put(ConditionOperator.EQ, WorkflowVariableType.BOOLEAN);
        BOOLEAN_OPERATOR.put(ConditionOperator.NEQ, WorkflowVariableType.BOOLEAN);
        BOOLEAN_OPERATOR.put(ConditionOperator.IS_TRUE, WorkflowVariableType.NULL);
        BOOLEAN_OPERATOR.put(ConditionOperator.IS_FALSE, WorkflowVariableType.NULL);
        BOOLEAN_OPERATOR.put(ConditionOperator.IN, WorkflowVariableType.ARRAY);
        BOOLEAN_OPERATOR.put(ConditionOperator.NIN, WorkflowVariableType.ARRAY);
        BOOLEAN_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        BOOLEAN_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.BOOLEAN, BOOLEAN_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> OBJECT_OPERATOR = new HashMap<>();
        OBJECT_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        OBJECT_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.OBJECT, OBJECT_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> MAP_OPERATOR = new HashMap<>();
        MAP_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        MAP_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.MAP, MAP_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> DATE_TIME_OPERATOR = new HashMap<>();
        DATE_TIME_OPERATOR.put(ConditionOperator.EQ, WorkflowVariableType.DATE_TIME);
        DATE_TIME_OPERATOR.put(ConditionOperator.NEQ, WorkflowVariableType.DATE_TIME);
        DATE_TIME_OPERATOR.put(ConditionOperator.GT, WorkflowVariableType.DATE_TIME);
        DATE_TIME_OPERATOR.put(ConditionOperator.GTE, WorkflowVariableType.DATE_TIME);
        DATE_TIME_OPERATOR.put(ConditionOperator.LT, WorkflowVariableType.DATE_TIME);
        DATE_TIME_OPERATOR.put(ConditionOperator.LTE, WorkflowVariableType.DATE_TIME);
        DATE_TIME_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        DATE_TIME_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.DATE_TIME, DATE_TIME_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> ARRAY_OPERATOR = new HashMap<>();
        ARRAY_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        ARRAY_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.ARRAY, ARRAY_OPERATOR);

        Map<ConditionOperator, WorkflowVariableType> NULL_OPERATOR = new HashMap<>();
        NULL_OPERATOR.put(ConditionOperator.EQ, WorkflowVariableType.NULL);
        NULL_OPERATOR.put(ConditionOperator.IS_EMPTY, WorkflowVariableType.NULL);
        NULL_OPERATOR.put(ConditionOperator.IS_NOT_EMPTY, WorkflowVariableType.NULL);
        INSTNACE.put(WorkflowVariableType.NULL, NULL_OPERATOR);
    }
}
