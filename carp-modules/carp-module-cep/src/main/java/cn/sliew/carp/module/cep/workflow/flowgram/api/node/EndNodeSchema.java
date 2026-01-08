package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import lombok.Data;

public class EndNodeSchema extends AbstractWorkflowNodeSchema<EndNodeSchema.EndNodeData> {

    private EndNodeSchema.EndNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.END;
    }

    @Override
    public EndNodeData getData() {
        return data;
    }

    @Data
    public static class EndNodeData extends AbstractWorkflowNodeData {

    }
}
