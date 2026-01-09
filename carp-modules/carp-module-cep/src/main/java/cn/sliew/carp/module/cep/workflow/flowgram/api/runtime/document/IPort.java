package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowPortType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
public class IPort {

    private String id;
    private INode node;
    private List<IEdge> edges = new ArrayList<>();
    private WorkflowPortType type;
    
    @Data
    @Accessors(chain = true)
    public static class CreatePortParams {
        private String id;
        private INode node;
        private WorkflowPortType type;
    }
}
