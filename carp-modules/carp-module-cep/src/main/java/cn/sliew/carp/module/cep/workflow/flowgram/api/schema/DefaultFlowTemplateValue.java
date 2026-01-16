package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;

@Data
public class DefaultFlowTemplateValue implements IFlowTemplateValue {

    private String type = "template";
    private String content;
    private IJsonSchema schema;
}
