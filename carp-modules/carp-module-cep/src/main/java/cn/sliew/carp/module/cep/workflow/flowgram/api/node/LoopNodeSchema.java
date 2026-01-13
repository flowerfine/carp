package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowRefValue;
import lombok.Data;

import java.util.Map;

@Data
public class LoopNodeSchema extends AbstractWorkflowNodeSchema<LoopNodeSchema.LoopNodeData> {

    private LoopNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.LOOP;
    }

    @Override
    public LoopNodeData getData() {
        return data;
    }

    @Data
    public static class LoopNodeData extends AbstractWorkflowNodeData {

        private IFlowRefValue loopFor;
        private Map<String, IFlowRefValue> loopOutputs;
    }
}
