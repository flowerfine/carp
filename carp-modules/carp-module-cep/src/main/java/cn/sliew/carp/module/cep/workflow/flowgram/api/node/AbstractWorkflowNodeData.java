package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IJsonSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowNodeData;
import lombok.Data;

import java.util.Map;

@Data
public class AbstractWorkflowNodeData implements IWorkflowNodeData {

    private String title;
    private Map<String, IValue> inputsValues;
    private IJsonSchema inputs;
    private IJsonSchema outputs;
}
