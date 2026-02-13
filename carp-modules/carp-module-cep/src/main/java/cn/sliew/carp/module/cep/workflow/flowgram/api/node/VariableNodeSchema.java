package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class VariableNodeSchema extends AbstractWorkflowNodeSchema<VariableNodeSchema.VariableNodeData> {

    private VariableNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.VARIABLE;
    }

    @Override
    public VariableNodeData getData() {
        return data;
    }

    @Data
    public static class VariableNodeData extends AbstractWorkflowNodeData {

        private List<VariableData> assign;
    }

    @Data
    public static class VariableData {
        private VariableType operator;
        private JsonNode left;
        private JsonNode right;
    }
}
