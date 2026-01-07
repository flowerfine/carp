package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IWorkflowEdgeSchema {

    String getSourceNodeID();
    String getTargetNodeID();
    String getSourcePortID();
    String getTargetPortID();
}
