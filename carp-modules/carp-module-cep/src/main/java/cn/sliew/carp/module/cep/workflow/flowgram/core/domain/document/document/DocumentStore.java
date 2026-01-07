package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IPort;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.PositionSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowPortType;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity.WorkflowRuntimeEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity.WorkflowRuntimeNode;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity.WorkflowRuntimePort;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class DocumentStore {

    private Map<String, WorkflowRuntimeNode> nodes;
    private Map<String, WorkflowRuntimeEdge> edges;
    private Map<String, WorkflowRuntimePort> ports;

    public static WorkflowRuntimeNode createNode(DocumentStore store, INode.CreateNodeParams params) {
        WorkflowRuntimeNode node = new WorkflowRuntimeNode(params);
        store.getNodes().put(node.getId(), node);
        return node;
    }

    public static WorkflowRuntimeEdge createEdge(DocumentStore store, IEdge.CreateEdgeParams params) {
        WorkflowRuntimeEdge edge = new WorkflowRuntimeEdge(params);
        store.getEdges().put(edge.getId(), edge);
        return edge;
    }

    public static WorkflowRuntimePort getOrCreatePort(DocumentStore store, IPort.CreatePortParams params) {
        if (store.getPorts().containsKey(params.getId())) {
            return store.getPorts().get(params.getId());
        }
        WorkflowRuntimePort port = new WorkflowRuntimePort(params);
        store.getPorts().put(port.getId(), port);
        return port;
    }

    // todo

    public static DocumentStore createStore(FlattenData params) {
        DocumentStore store = new DocumentStore()
                .setNodes(new HashMap<>())
                .setEdges(new HashMap<>())
                .setPorts(new HashMap<>());
        createNode(store, new INode.CreateNodeParams()
                .setId(NodeType.ROOT.getValue())
                .setType(NodeType.ROOT)
                .setName(NodeType.ROOT.getLabel())
                .setPosition(new PositionSchema().setX(0).setY(0))
        );

        // create nodes
        params.getFlattenSchema().getNodes().forEach(nodeSchema -> {
            INode.CreateNodeParams nodeParams = new INode.CreateNodeParams()
                    .setId(nodeSchema.getId())
                    .setType(nodeSchema.getType())
                    .setName(nodeSchema.getType().getLabel() + "-" + nodeSchema.getId() + "-untitled")
                    .setPosition(nodeSchema.getMeta().getPosition());
            if (Objects.nonNull(nodeSchema.getData())) {
                nodeParams.setVariable(new INode.NodeDeclare()
                                .setInputsValues(nodeSchema.getData().getInputsValues())
                                .setInputs(nodeSchema.getData().getInputs())
                                .setOutputs(nodeSchema.getData().getOutputs())
                        )
                        .setData(nodeSchema.getData());
            }
            createNode(store, nodeParams);
        });

        // create node relations
        params.getNodeBlocks().forEach((parentID, blockIDs) -> {
            WorkflowRuntimeNode parent = store.getNodes().get(parentID);
            List<WorkflowRuntimeNode> children = blockIDs.stream().map(id -> store.getNodes().get(id)).filter(Objects::nonNull).collect(Collectors.toList());
            children.forEach(child -> {
                child.setParent(parent);
                parent.addChild(child);
            });
        });

        // create edges & ports
        params.getFlattenSchema().getEdges().forEach(edgeSchema -> {
            String id = WorkflowRuntimeEdge.createID(edgeSchema);
            WorkflowRuntimeNode from = store.getNodes().get(edgeSchema.getSourceNodeID());
            WorkflowRuntimeNode to = store.getNodes().get(edgeSchema.getTargetNodeID());
            if (Objects.isNull(from) || Objects.isNull(to)) {
                throw new RuntimeException("Invalid edge schema ID: " + id + ", from: " + edgeSchema.getSourceNodeID() + ", to: " + edgeSchema.getTargetNodeID());
            }
            WorkflowRuntimeEdge edge = createEdge(store, new IEdge.CreateEdgeParams()
                    .setId(id)
                    .setFrom(from)
                    .setTo(to)
            );

            // create from port
            String sourcePortID = "defaultOutput";
            if (StringUtils.isNotBlank(edgeSchema.getSourcePortID())) {
                sourcePortID = edgeSchema.getSourcePortID();
            }
            WorkflowRuntimePort fromPort = getOrCreatePort(store, new IPort.CreatePortParams()
                    .setNode(from)
                    .setId(sourcePortID)
                    .setType(WorkflowPortType.OUTPUT)
            );
            // build relation
            fromPort.addEdge(edge);
            edge.setFromPort(fromPort);
            from.addPort(fromPort);
            from.addOutputEdge(edge);

            // create to port
            String targetPortID = "defaultInput";
            if (StringUtils.isNotBlank(edgeSchema.getTargetPortID())) {
                targetPortID = edgeSchema.getTargetPortID();
            }
            WorkflowRuntimePort toPort = getOrCreatePort(store, new IPort.CreatePortParams()
                    .setNode(to)
                    .setId(targetPortID)
                    .setType(WorkflowPortType.INPUT)
            );
            // build relation
            toPort.addEdge(edge);
            edge.setToPort(toPort);
            to.addPort(toPort);
            to.addInputEdge(edge);
        });

        return store;
    }


}
