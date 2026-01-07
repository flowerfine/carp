package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.iocenter;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IOData {

    private WorkflowInputs inputs;
    private WorkflowOutputs outputs;
}
