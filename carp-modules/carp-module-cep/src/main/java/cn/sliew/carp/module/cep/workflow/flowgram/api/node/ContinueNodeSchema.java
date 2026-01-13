package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import lombok.Data;

public class ContinueNodeSchema extends AbstractWorkflowNodeSchema<ContinueNodeSchema.ContinueNodeData> {

    private ContinueNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.END;
    }

    @Override
    public ContinueNodeData getData() {
        return data;
    }

    @Data
    public static class ContinueNodeData extends AbstractWorkflowNodeData {

    }
}
