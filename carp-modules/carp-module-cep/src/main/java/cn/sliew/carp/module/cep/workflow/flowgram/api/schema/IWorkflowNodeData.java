package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import java.util.Map;

public interface IWorkflowNodeData {

    String getTitle();
    Map<String, IValue> getInputsValues();
    IJsonSchema getInputs();
    IJsonSchema getOutputs();

}
