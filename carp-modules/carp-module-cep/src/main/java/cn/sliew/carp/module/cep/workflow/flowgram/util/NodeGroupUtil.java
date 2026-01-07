package cn.sliew.carp.module.cep.workflow.flowgram.util;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NodeGroupUtil {
    ;

    public static NodeComparisonResult compareNodeGroups(List<List<INode>> groupA, List<List<INode>> groupB) {
        Map<String, INode> mapA = groupA.stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toMap(INode::getId, Function.identity()));
        Map<String, INode> mapB = groupB.stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toMap(INode::getId, Function.identity()));

        List<INode> common = new ArrayList<>();
        List<INode> uniqueToA = new ArrayList<>();
        List<INode> uniqueToB = new ArrayList<>();

        mapA.forEach((id, node) -> {
            if (mapB.containsKey(id)) {
                common.add(node);
            } else {
                uniqueToA.add(node);
            }
        });
        mapB.forEach((id, node) -> {
            if (mapA.containsKey(id)) {
                common.add(node);
            } else {
                uniqueToB.add(node);
            }
        });
        return new NodeComparisonResult()
                .setCommon(common)
                .setUniqueToA(uniqueToA)
                .setUniqueToB(uniqueToB);
    }

    @Data
    @Accessors(chain = true)
    public static class NodeComparisonResult {

        private List<INode> common;
        private List<INode> uniqueToA;
        private List<INode> uniqueToB;
    }
}
