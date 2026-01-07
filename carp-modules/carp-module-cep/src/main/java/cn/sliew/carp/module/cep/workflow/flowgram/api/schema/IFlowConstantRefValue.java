package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IFlowConstantRefValue extends IFlowConstantValue, IFlowRefValue {

    @Override
    String getType();

    @Override
    Object getContent();
}
