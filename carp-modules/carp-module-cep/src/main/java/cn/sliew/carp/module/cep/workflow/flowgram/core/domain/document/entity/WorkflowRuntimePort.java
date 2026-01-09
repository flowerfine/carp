package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IPort;

public class WorkflowRuntimePort extends IPort {

    public WorkflowRuntimePort(CreatePortParams params) {
        setId(params.getId());
        setNode(params.getNode());
        setType(params.getType());
    }

    public void addEdge(IEdge edge) {
        getEdges().add(edge);
    }
}
