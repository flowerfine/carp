package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.AbstractWorkflowNodeData;
import lombok.Data;

public interface IWorkflowGroupSchema extends IWorkflowNodeSchema<IWorkflowGroupSchema.WorkflowGroupData> {

    @Override
    WorkflowGroupData getData();

    @Data
    class WorkflowGroupData extends AbstractWorkflowNodeData {

        private String color;
        private String parentID;
        private String[] blockIDs;
    }
}
