package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IPort;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowPortType;
import cn.sliew.carp.module.cep.workflow.flowgram.util.NodeUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowRuntimeNode extends INode {

    private List<IPort> ports;
    private List<IEdge> inputEdges;
    private List<IEdge> outputEdges;

    public WorkflowRuntimeNode(CreateNodeParams params) {
        setId(params.getId());
        setType(params.getType());
        setName(params.getName());
        setPosition(params.getPosition());
        setDeclare(params.getVariable());
        setData(params.getData());

        ports = new ArrayList<>();
        inputEdges = new ArrayList<>();
        outputEdges = new ArrayList<>();
    }

    @Override
    public PortInfo getPorts() {
        return new PortInfo()
                .setInputs(ports.stream().filter(port -> port.getType() == WorkflowPortType.INPUT).collect(Collectors.toList()))
                .setOutputs(ports.stream().filter(port -> port.getType() == WorkflowPortType.OUTPUT).collect(Collectors.toList()));
    }

    @Override
    public EdgeInfo getEdges() {
        return new EdgeInfo()
                .setInputs(inputEdges)
                .setOutputs(outputEdges);
    }

    public void addChild(INode child) {
        getChildren().add(child);
    }

    public void addPort(IPort port) {
        ports.add(port);
    }

    public void addInputEdge(IEdge edge) {
        inputEdges.add(edge);
        getPrev().add(edge.getFrom());
    }

    public void addOutputEdge(IEdge edge ) {
        outputEdges.add(edge);
        getNext().add(edge.getTo());
    }

    @Override
    public List<INode> getSuccessors() {
        return NodeUtil.traverseNodes(this, node -> node.getNext());
    }

    @Override
    public List<INode> getPredecessors() {
        return NodeUtil.traverseNodes(this, node -> node.getPrev());
    }

    @Override
    public boolean isBranch() {
        return CollectionUtils.size(getPorts().getOutputs()) > 1;
    }
}
