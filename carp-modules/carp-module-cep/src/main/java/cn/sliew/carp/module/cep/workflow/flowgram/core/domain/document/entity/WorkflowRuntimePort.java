package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IPort;

import java.util.ArrayList;

public class WorkflowRuntimePort extends IPort {

    public WorkflowRuntimePort(CreatePortParams params) {
        setId(params.getId());
        setNode(params.getNode());
        setType(params.getType());
        setEdges(new ArrayList<>());
    }

    public void addEdge(IEdge edge) {
        getEdges().add(edge);
    }
}
