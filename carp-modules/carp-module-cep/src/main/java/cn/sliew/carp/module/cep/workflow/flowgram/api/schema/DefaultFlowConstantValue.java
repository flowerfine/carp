package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;

@Data
public class DefaultFlowConstantValue implements IFlowConstantValue {

    private String type = "constant";
    private Object content;
    private IJsonSchema schema;
}
