package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import java.util.List;

public interface IFlowRefValue extends IValue {

    @Override
    default String getType() {
        return "ref";
    }
}
