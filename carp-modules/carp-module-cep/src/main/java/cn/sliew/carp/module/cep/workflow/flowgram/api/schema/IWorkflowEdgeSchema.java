package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = DefaultWorkflowEdgeSchema.class)
public interface IWorkflowEdgeSchema {

    String getSourceNodeID();
    String getTargetNodeID();
    String getSourcePortID();
    String getTargetPortID();
}
