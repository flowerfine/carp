package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.document;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IDocument;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WorkflowRuntimeDocument extends IDocument {

    private DocumentStore store;

    public WorkflowRuntimeDocument() {
        setId(UUIDUtil.randomUUId());
    }

    public INode getNode(String id) {
        return store.getNodes().get(id);
    }

    public IEdge getEdge(String id) {
        return store.getEdges().get(id);
    }

    @Override
    public List<INode> getNodes() {
        return new ArrayList<>(store.getNodes().values());
    }

    @Override
    public List<IEdge> getEdges() {
        return new ArrayList<>(store.getEdges().values());
    }

    public INode getRoot() {
        INode node = getNode(NodeType.ROOT.getValue());
        if (Objects.isNull(node)) {
            throw new RuntimeException("Root node not found");
        }
        return node;
    }

    @Override
    public INode getStart() {
        Optional<INode> optional = getNodes().stream()
                .filter(node -> node.getType() == NodeType.START)
                .findAny();
        return optional.orElseThrow(() -> new RuntimeException("Start node not found"));
    }

    @Override
    public INode getEnd() {
        Optional<INode> optional = getNodes().stream()
                .filter(node -> node.getType() == NodeType.END)
                .findAny();
        return optional.orElseThrow(() -> new RuntimeException("End node not found"));
    }

    @Override
    public void init(IWorkflowSchema schema) {
        FlattenData flattenData = FlatUtil.flatSchema(schema);
        this.store = DocumentStore.createStore(flattenData);
    }

    @Override
    public void dispose() {
        this.store.getNodes().clear();
        this.store.getEdges().clear();
        this.store.getPorts().clear();
    }
}
