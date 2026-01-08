package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;

@Data
public class DefaultFlowTemplateValue implements IFlowTemplateValue {

    private String type;
    private String content;
}
