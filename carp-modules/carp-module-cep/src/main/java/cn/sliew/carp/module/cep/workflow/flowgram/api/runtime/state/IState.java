package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.state;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.*;
import lombok.Data;

import java.util.Map;

@Data
public abstract class IState {

    private String id;
    private IVariableStore variableStore;

    public abstract void init(IWorkflowSchema schema);
    public abstract void dispose();
    public abstract WorkflowInputs getNodeInputs(INode node);
    public abstract void setNodeOutputs(INode node, WorkflowOutputs outputs);
    public abstract WorkflowInputs parseInputs(Map<String, Object> values, IJsonSchema declare);
    public abstract IVariableParseResult parseRef(IFlowRefValue ref);
    public abstract IVariableParseResult parseTemplate(IFlowTemplateValue template);
    public abstract IVariableParseResult parseFlowValue(IValue flowValue, WorkflowVariableType declareType);
    public abstract boolean isExecutedNode(INode node);
    public abstract void addExecutedNode(INode node);
}
