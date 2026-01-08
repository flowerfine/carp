package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = DefaultWorkflowSchema.class)
public interface IWorkflowSchema {

    List<IWorkflowNodeSchema> getNodes();
    List<IWorkflowEdgeSchema> getEdges();
    List<IWorkflowGroupSchema> getGroups();
    IJsonSchema getGlobalVariable();
}
