package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import lombok.Data;

@Data
public class LLMNodeSchema extends AbstractWorkflowNodeSchema<LLMNodeSchema.LLMNodeData> {

    private LLMNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.LLM;
    }

    @Override
    public LLMNodeData getData() {
        return data;
    }

    @Data
    public static class LLMNodeData extends AbstractWorkflowNodeData {

    }
}
