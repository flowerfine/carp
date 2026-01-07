package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

public interface IFlowTemplateValue extends IValue {

    @Override
    default String getType() {
        return "template";
    }
}
