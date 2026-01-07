package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IFlowValue extends IFlowConstantValue, IFlowRefValue, IFlowExpressionValue, IFlowTemplateValue {

    @Override
    String getType();

    @Override
    Object getContent();
}
