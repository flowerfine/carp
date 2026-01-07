package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;

import java.util.List;

public interface IWorkflowNodeSchema<DATA extends IWorkflowNodeData> {

    String getId();
    NodeType getType();
    IWorkflowNodeMetaSchema getMeta();
    DATA getData();
    List<IWorkflowNodeSchema> getBlocks();
    List<IWorkflowEdgeSchema> getEdges();
}
