package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.iocenter;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.iocenter.IIOCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.iocenter.IOData;

public class WorkflowRuntimeIOCenter extends IIOCenter {

    @Override
    public void init(WorkflowInputs inputs) {
        setInputs(inputs);
    }

    @Override
    public void dispose() {

    }

    @Override
    public IOData export() {
        return new IOData()
                .setInputs(getInputs())
                .setOutputs(getOutputs());
    }
}
