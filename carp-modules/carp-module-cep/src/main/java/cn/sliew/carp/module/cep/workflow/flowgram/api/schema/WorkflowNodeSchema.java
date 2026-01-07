package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class WorkflowNodeSchema<DATA extends IWorkflowNodeData> implements IWorkflowNodeSchema<DATA> {

    private String id;
    private NodeType type;
    private WorkflowNodeMetaSchema meta;
    private DATA data;
    private List<IWorkflowNodeSchema> blocks;
    private List<IWorkflowEdgeSchema> edges;
}
