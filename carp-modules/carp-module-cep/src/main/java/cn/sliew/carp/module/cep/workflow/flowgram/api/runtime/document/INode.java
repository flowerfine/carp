package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IJsonSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.PositionSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
public abstract class INode<T> {

    private String id;
    private NodeType type;
    private String name;
    private PositionSchema position;
    private NodeDeclare declare;
    private T data;

    private INode parent;
    private List<INode> children;

    private List<INode> prev;
    private List<INode> next;

    public abstract PortInfo getPorts();
    public abstract EdgeInfo getEdges();

    public abstract List<INode> getSuccessors();
    public abstract List<INode> getPredecessors();

    @Data
    @Accessors(chain = true)
    public static class PortInfo {
        private List<IPort> inputs;
        private List<IPort> outputs;
    }

    @Data
    @Accessors(chain = true)
    public static class EdgeInfo {
        private List<IEdge> inputs;
        private List<IEdge> outputs;
    }

    @Data
    @Accessors(chain = true)
    public static class NodeDeclare {
        private Map<String, Object> inputsValues;
        private IJsonSchema inputs;
        private IJsonSchema outputs;
    }

    @Data
    @Accessors(chain = true)
    public static class CreateNodeParams {
        private String id;
        private NodeType type;
        private String name;
        private PositionSchema position;
        private NodeDeclare variable;
        private Object data;
    }
}
