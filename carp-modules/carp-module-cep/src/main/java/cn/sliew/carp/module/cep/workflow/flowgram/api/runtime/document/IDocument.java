package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import lombok.Data;

import java.util.List;

@Data
public abstract class IDocument {

    private String id;

    public abstract List<INode> getNodes();
    public abstract List<IEdge> getEdges();
    public abstract INode getRoot();
    public abstract INode getStart();
    public abstract INode getEnd();

    public abstract void init(IWorkflowSchema schema);
    public abstract void dispose();
}
