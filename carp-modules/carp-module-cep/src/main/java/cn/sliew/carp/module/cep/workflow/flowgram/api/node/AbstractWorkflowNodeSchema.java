package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowEdgeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeMetaSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeSchema;
import lombok.Data;

import java.util.List;

@Data
public abstract class AbstractWorkflowNodeSchema<DATA extends IWorkflowNodeData> implements IWorkflowNodeSchema<DATA> {

    private String id;
    private IWorkflowNodeMetaSchema meta;
    private List<IWorkflowNodeSchema> blocks;
    private List<IWorkflowEdgeSchema> edges;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public IWorkflowNodeMetaSchema getMeta() {
        return meta;
    }

    @Override
    public List<IWorkflowNodeSchema> getBlocks() {
        return blocks;
    }

    @Override
    public List<IWorkflowEdgeSchema> getEdges() {
        return edges;
    }
}
