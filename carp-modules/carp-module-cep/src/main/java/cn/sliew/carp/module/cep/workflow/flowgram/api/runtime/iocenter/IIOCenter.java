package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.iocenter;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import lombok.Data;

@Data
public abstract class IIOCenter {

    private WorkflowInputs inputs;
    private WorkflowOutputs outputs;

    public abstract void init(WorkflowInputs inputs);
    public abstract void dispose();
    public abstract IOData export();
}
