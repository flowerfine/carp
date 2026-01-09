package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowEdgeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeMetaSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeSchema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractWorkflowNodeSchema<DATA extends IWorkflowNodeData> implements IWorkflowNodeSchema<DATA> {

    private String id;
    private IWorkflowNodeMetaSchema meta;
    private List<IWorkflowNodeSchema> blocks = new ArrayList<>();
    private List<IWorkflowEdgeSchema> edges = new ArrayList<>();
}
