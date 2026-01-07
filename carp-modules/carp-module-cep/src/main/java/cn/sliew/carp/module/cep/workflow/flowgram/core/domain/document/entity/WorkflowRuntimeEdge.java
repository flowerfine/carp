package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.entity;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.IEdge;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowEdgeSchema;
import org.apache.commons.lang3.StringUtils;

public class WorkflowRuntimeEdge extends IEdge {

    public WorkflowRuntimeEdge(CreateEdgeParams params) {
        setId(params.getId());
        setFrom(params.getFrom());
        setTo(params.getTo());
    }

    public static String createID(IWorkflowEdgeSchema schema) {
        String sourcePart = StringUtils.isNotBlank(schema.getSourcePortID())
                ? schema.getSourceNodeID() + ":" + schema.getSourcePortID()
                : schema.getSourceNodeID();
        String targetPart = StringUtils.isNotBlank(schema.getTargetPortID())
                ? schema.getTargetNodeID() + ":" + schema.getTargetPortID()
                : schema.getTargetNodeID();
        return sourcePart + "-" + targetPart;
    }
}
