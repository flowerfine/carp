package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;

@Data
public class DefaultFlowExpressionValue implements IFlowExpressionValue {

    private String type = "expression";
    private String content;
}
