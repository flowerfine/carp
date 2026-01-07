package cn.sliew.carp.module.cep.workflow.flowgram.util;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public enum NodeUtil {
    ;

    public static List<INode> traverseNodes(INode startNode, Function<INode, List<INode>> getConnectedNodes) {
        Set<String> visited = new HashSet<>();
        List<INode> result = new ArrayList<>();
        traverse(visited, result, startNode, getConnectedNodes);
        return result;
    }

    private static void traverse(Set<String> visited, List<INode> result, INode startNode, Function<INode, List<INode>> getConnectedNodes) {
        for (INode connectedNode : getConnectedNodes.apply(startNode)) {
            if (!visited.contains(connectedNode.getId())) {
                visited.add(connectedNode.getId());
                result.add(connectedNode);
                traverse(visited, result, connectedNode, getConnectedNodes);
            }
        }
    }
}
