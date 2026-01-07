package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import lombok.Data;

@Data
public class BreakNodeSchema extends AbstractWorkflowNodeSchema<BreakNodeSchema.BreakNodeData> {

    private BreakNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.BREAK;
    }

    @Override
    public BreakNodeData getData() {
        return data;
    }

    @Data
    public static class BreakNodeData extends AbstractWorkflowNodeData {

    }
}
