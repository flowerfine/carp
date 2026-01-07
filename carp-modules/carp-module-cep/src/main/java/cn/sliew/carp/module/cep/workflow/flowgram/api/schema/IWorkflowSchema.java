package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IWorkflowSchema {

    IWorkflowNodeSchema[] getNodes();
    IWorkflowEdgeSchema[] getEdges();
    IWorkflowGroupSchema[] getGroups();
    IJsonSchema getGlobalVariable();
}
