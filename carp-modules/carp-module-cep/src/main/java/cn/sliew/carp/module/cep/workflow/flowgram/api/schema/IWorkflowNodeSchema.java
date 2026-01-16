package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = DefaultWorkflowNodeMetaSchema.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartNodeSchema.class, name = "start"),
        @JsonSubTypes.Type(value = EndNodeSchema.class, name = "end"),
        @JsonSubTypes.Type(value = HTTPNodeSchema.class, name = "http"),
        @JsonSubTypes.Type(value = BreakNodeSchema.class, name = "break"),
        @JsonSubTypes.Type(value = CodeNodeSchema.class, name = "code"),
        @JsonSubTypes.Type(value = VariableNodeSchema.class, name = "variable"),
        @JsonSubTypes.Type(value = ConditionNodeSchema.class, name = "condition"),
})
public interface IWorkflowNodeSchema<DATA extends IWorkflowNodeData> {

    String getId();
    NodeType getType();
    IWorkflowNodeMetaSchema getMeta();
    DATA getData();
    List<IWorkflowNodeSchema> getBlocks();
    List<IWorkflowEdgeSchema> getEdges();
}
