package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SnapshotData {

    private String nodeID;
    private WorkflowInputs inputs;
    private WorkflowOutputs outputs;
    private Object data;
    private String branch;
    private String error;
}
