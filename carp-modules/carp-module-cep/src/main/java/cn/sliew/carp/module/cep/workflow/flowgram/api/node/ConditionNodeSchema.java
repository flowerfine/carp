package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowRefValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IValue;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
public class ConditionNodeSchema extends AbstractWorkflowNodeSchema<ConditionNodeSchema.ConditionNodeData> {

    private ConditionNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.CONDITION;
    }

    @Override
    public ConditionNodeData getData() {
        return data;
    }

    @Data
    public static class ConditionNodeData extends AbstractWorkflowNodeData {

        private List<ConditionItem> conditions;
    }

    @Data
    @Accessors(chain = true)
    public static class ConditionItem {

        private String key;
        private ConditionItemValue value;
    }

    @Data
    @Accessors(chain = true)
    public static class ConditionItemValue {
        private IFlowRefValue left;
        private ConditionOperator operator;
        private IValue right;
    }


}
