package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.state;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.state.IState;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WorkflowRuntimeState extends IState {

    private Set<String> executedNodes;

    public WorkflowRuntimeState() {
        setId(UUIDUtil.randomUUId());
    }

    @Override
    public void init(IWorkflowSchema schema) {
        if (Objects.nonNull(schema)) {
            setGlobalVariable(schema.getGlobalVariable());
        }
        this.executedNodes = new HashSet<>();
    }

    @Override
    public void dispose() {
        this.executedNodes.clear();
    }

    @Override
    public WorkflowInputs getNodeInputs(INode node) {
        return null;
    }

    @Override
    public void setNodeOutputs(SetNodeOutputParam param) {

    }

    @Override
    public WorkflowInputs parseInputs(ParseInputParam param) {
        return null;
    }

    @Override
    public IVariableParseResult parseRef(Object ref) {
        return null;
    }

    @Override
    public IVariableParseResult parseTemplate(Object template) {
        return null;
    }

    @Override
    public IVariableParseResult parseFlowValue(IFlowValue flowValue, WorkflowVariableType declareType) {
        if (StringUtils.isBlank(flowValue.getType())) {
            throw new RuntimeException("Invalid flow value type: " + flowValue.getType());
        }
        if (StringUtils.equals(flowValue.getType(), "constant")) {
            Object value = parseJSONContent(flowValue.getContent(), declareType);
            // 解析类型
        }
        return null;
    }

    @Override
    public boolean isExecutedNode(INode node) {
        return executedNodes.contains(node.getId());
    }

    @Override
    public void addExecutedNode(INode node) {
        executedNodes.add(node.getId());
    }

    private Object parseJSONContent(Object jsonContent, WorkflowVariableType declareType) {
        if (Objects.nonNull(declareType)
                && (declareType == WorkflowVariableType.OBJECT
                || declareType == WorkflowVariableType.ARRAY
                || declareType == WorkflowVariableType.MAP)
                && jsonContent instanceof String) {
            // todo 解析 json 为
            return jsonContent;
        }
        return jsonContent;
    }

    private void setGlobalVariable(IJsonSchema globalVariableDeclare) {
        if (Objects.isNull(globalVariableDeclare)) {
            return;
        }

        if (!StringUtils.equals(globalVariableDeclare.getType(), JsonSchemaBasicType.OBJECT.getValue())
                && MapUtils.isEmpty(globalVariableDeclare.getProperties())) {
            return;
        }

        globalVariableDeclare.getProperties().forEach((key, typeInfo) -> {
            if (Objects.isNull(key) || Objects.isNull(typeInfo)) {
                return;
            }
            getVariableStore().setVariable((IVariableStore.SetVariableParam) new IVariableStore.SetVariableParam()
                    .setNodeID("global")
                    .setKey(key)
                    .setValue(parseJSONContent(typeInfo.getDefaultValue(), WorkflowVariableType.of(typeInfo.getType())))
                    .setType(WorkflowVariableType.of(typeInfo.getType()))
                    .setItemsType(WorkflowVariableType.of(typeInfo.getItems().getType()))
            );
        });
    }
}
