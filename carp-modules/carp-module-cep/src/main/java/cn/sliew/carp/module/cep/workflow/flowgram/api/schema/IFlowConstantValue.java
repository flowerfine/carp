package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IFlowConstantValue extends IValue {

    @Override
    default String getType() {
        return "constant";
    }
}
