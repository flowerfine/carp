package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.IMessageCenter;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message.WorkflowMessageType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.StatusData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class IReport {

    private String id;
    private WorkflowInputs inputs;
    private WorkflowOutputs outputs;
    private StatusData workflowStatus;
    private Map<String, NodeReport> reports;
    private Map<WorkflowMessageType, List<IMessageCenter.IMessage>> messages;
}
