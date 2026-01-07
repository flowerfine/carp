package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import lombok.Data;

@Data
public class StartNodeSchema extends AbstractWorkflowNodeSchema<StartNodeSchema.StartNodeData> {

    private StartNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.START;
    }

    @Override
    public StartNodeData getData() {
        return data;
    }

    @Data
    public static class StartNodeData extends AbstractWorkflowNodeData {

    }
}
