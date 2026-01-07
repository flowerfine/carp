package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IFlowExpressionValue extends IValue {

    @Override
    default String getType() {
        return "expression";
    }
}
