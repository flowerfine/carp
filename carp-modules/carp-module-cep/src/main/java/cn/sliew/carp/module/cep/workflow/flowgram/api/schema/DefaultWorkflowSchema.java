package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DefaultWorkflowSchema implements IWorkflowSchema {

    private List<IWorkflowNodeSchema> nodes;
    private List<IWorkflowEdgeSchema> edges;
    private List<IWorkflowGroupSchema> groups;
    private IJsonSchema globalVariable;
}
