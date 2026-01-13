package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.ConditionOperator;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConditionValue {

    private String key;
    private Object leftValue;
    private Object rightValue;
    private WorkflowVariableType leftType;
    private WorkflowVariableType rightType;
    private ConditionOperator operator;
}
