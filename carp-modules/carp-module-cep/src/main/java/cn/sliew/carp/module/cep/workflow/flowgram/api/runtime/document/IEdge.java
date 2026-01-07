package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class IEdge {

    private String id;
    private INode from;
    private INode to;
    private IPort fromPort;
    private IPort toPort;

    @Data
    @Accessors(chain = true)
    public static class CreateEdgeParams {
        private String id;
        private INode from;
        private INode to;
    }
}
