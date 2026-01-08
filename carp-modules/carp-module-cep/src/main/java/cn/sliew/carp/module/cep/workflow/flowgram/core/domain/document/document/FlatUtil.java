package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.*;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity.WorkflowRuntimeEdge;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public enum FlatUtil {
    ;

    public static void flatLayer(FlattenData data, IWorkflowNodeSchema nodeSchema) {
        if (Objects.nonNull(nodeSchema.getBlocks())) {
            List<IWorkflowNodeSchema> blocks = nodeSchema.getBlocks();
            data.getFlattenSchema().getNodes().addAll(blocks);
            List<String> blockIDs = new ArrayList<>();
            blocks.forEach(block -> {
                blockIDs.add(block.getId());
                // 递归处理子节点的 blocks 和 edges
                if (CollectionUtils.isNotEmpty(block.getBlocks())) {
                    flatLayer(data, block);
                }
            });
            data.getNodeBlocks().put(nodeSchema.getId(), blockIDs);
            nodeSchema.getBlocks().clear();
        }

        if (Objects.nonNull(nodeSchema.getEdges())) {
            List<IWorkflowEdgeSchema> edges = nodeSchema.getEdges();
            data.getFlattenSchema().getEdges().addAll(edges);
            List<String> edgeIDs = new ArrayList<>();
            edges.forEach(edge -> {
                String edgeID = WorkflowRuntimeEdge.createID(edge);
                edgeIDs.add(edgeID);
            });
            data.getNodeEdges().put(nodeSchema.getId(), edgeIDs);
            nodeSchema.getEdges().clear();
        }
    }

    public static FlattenData flatSchema(IWorkflowSchema schema) {
        List<IWorkflowNodeSchema> nodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schema.getNodes())) {
            nodes = schema.getNodes();
        }
        List<IWorkflowEdgeSchema> edges = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schema.getEdges())) {
            edges = schema.getEdges();
        }

        FlattenData data = new FlattenData()
                .setFlattenSchema(new DefaultWorkflowSchema()
                        .setNodes(new ArrayList<>())
                        .setEdges(new ArrayList<>())
                )
                .setNodeBlocks(new HashMap<>())
                .setNodeEdges(new HashMap<>());

        WorkflowNodeSchema root = new WorkflowNodeSchema()
                .setId(NodeType.ROOT.getValue())
                .setType(NodeType.ROOT)
                .setBlocks(nodes)
                .setEdges(edges)
                .setMeta(new DefaultWorkflowNodeMetaSchema().setPosition(new PositionSchema().setX(0).setY(0)));
        
        flatLayer(data, root);

        return data;
    }
}
