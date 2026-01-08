package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IFlowRefValue extends IValue {

    @Override
    default String getType() {
        return "ref";
    }
}
