package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import lombok.Data;

@Data
public class CodeNodeSchema extends AbstractWorkflowNodeSchema<CodeNodeSchema.CodeNodeData> {

    private CodeNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.CODE;
    }

    @Override
    public CodeNodeData getData() {
        return data;
    }

    @Data
    public static class CodeNodeData extends AbstractWorkflowNodeData {
        
        private Script script;
    }

    @Data
    public static class Script {

        private String language;
        private String content;
    }

}
