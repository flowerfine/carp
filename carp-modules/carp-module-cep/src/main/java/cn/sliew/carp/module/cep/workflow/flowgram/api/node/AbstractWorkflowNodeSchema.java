package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.*;
import lombok.Data;

import java.util.List;

@Data
public abstract class AbstractWorkflowNodeSchema<DATA extends IWorkflowNodeData> implements IWorkflowNodeSchema<DATA> {

    private String id;
    private IWorkflowNodeMetaSchema meta;
    private List<IWorkflowNodeSchema> blocks;
    private List<IWorkflowEdgeSchema> edges;

    @Override
    public List<IWorkflowNodeSchema> getBlocks() {
        return blocks;
    }
}
