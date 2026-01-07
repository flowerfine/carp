package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.state;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IJsonSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
public abstract class IState {

    private String id;
    private IVariableStore variableStore;

    public abstract void init(IWorkflowSchema schema);
    public abstract void dispose();
    public abstract WorkflowInputs getNodeInputs(INode node);
    public abstract void setNodeOutputs(SetNodeOutputParam param);
    public abstract WorkflowInputs parseInputs(ParseInputParam param);
    public abstract IVariableParseResult parseRef(Object ref);
    public abstract IVariableParseResult parseTemplate(Object template);
    public abstract IVariableParseResult parseFlowValue(IFlowValue flowValue, WorkflowVariableType declareType);
    public abstract boolean isExecutedNode(INode node);
    public abstract void addExecutedNode(INode node);

    @Data
    @Accessors(chain = true)
    public static class SetNodeOutputParam {
        private INode node;
        private WorkflowOutputs outputs;
    }

    @Data
    public static class ParseInputParam {
        private Map<String, Object> values;
        private IJsonSchema declare;
    }

    @Data
    public static class ParseFlowValueParam {
        private Object flowValue;
        private WorkflowVariableType declareType;
    }
}
