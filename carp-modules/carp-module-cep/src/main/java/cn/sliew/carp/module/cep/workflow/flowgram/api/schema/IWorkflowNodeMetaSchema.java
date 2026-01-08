package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = DefaultWorkflowNodeMetaSchema.class)
public interface IWorkflowNodeMetaSchema {

    PositionSchema getPosition();

    PositionSchema getCanvasPosition();
}
