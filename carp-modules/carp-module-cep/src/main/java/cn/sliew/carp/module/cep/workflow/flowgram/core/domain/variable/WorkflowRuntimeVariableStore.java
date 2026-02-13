package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.variable;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariable;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import cn.sliew.carp.module.cep.workflow.flowgram.util.WorkflowRuntimeType;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class WorkflowRuntimeVariableStore extends IVariableStore {

    private WorkflowRuntimeVariableStore parent;

    public WorkflowRuntimeVariableStore() {
        setId(UUIDUtil.randomUUId());
    }

    @Override
    public void setParent(IVariableStore parent) {
        this.parent = (WorkflowRuntimeVariableStore) parent;
    }

    @Override
    public void setVariable(SetVariableParam param) {
        Map<String, Map<String, IVariable>> store = getStore();
        Map<String, IVariable> nodeStore = store.computeIfAbsent(param.getNodeID(), key -> new HashMap<>());
        IVariable variable = WorkflowRuntimeVariable.create(param);
        nodeStore.put(param.getKey(), variable);
    }

    @Override
    public void setValue(SetValueParam param) {
        Map<String, Map<String, IVariable>> store = getStore();
        Map<String, IVariable> nodeStore = store.computeIfAbsent(param.getNodeID(), key -> new HashMap<>());
        if (!nodeStore.containsKey(param.getVariableKey())) {
            SetVariableParam setVariableParam = new SetVariableParam()
                    .setNodeID(param.getNodeID())
                    .setKey(param.getVariableKey());
            setVariableParam.setType(WorkflowVariableType.OBJECT);
            IVariable variable = WorkflowRuntimeVariable.create(setVariableParam);
            nodeStore.put(param.getVariableKey(), variable);
        }

        IVariable variable = nodeStore.get(param.getVariableKey());
        if (ArrayUtils.isEmpty(param.getVariablePath())) {
            variable.setValue(param.getValue());
        } else {
            try {
                // 按照路径深层次访问，如 a.b.c.d.e
                String variablePathStr = Arrays.stream(param.getVariablePath()).collect(Collectors.joining("."));
                PropertyUtils.setNestedProperty(variable.getValue(), variablePathStr, param.getValue());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<IVariableParseResult> getValue(String nodeID, String variableKey, String[] variablePath) {
        Map<String, IVariable> nodeStore = globalGet(nodeID);
        if (Objects.isNull(nodeStore)) {
            return Optional.empty();
        }
        IVariable variable = nodeStore.get(variableKey);
        if (Objects.isNull(variable)) {
            return Optional.empty();
        }

        IVariableParseResult parseResult = new IVariableParseResult()
            .setNodeId(nodeID)
            .setKey(variableKey);
        if (ArrayUtils.isEmpty(variablePath)) {
            parseResult
                    .setValue(variable.getValue())
                    .setType(variable.getType())
                    .setItemsType(variable.getItemsType());
            return Optional.of(parseResult);
        } else {
            try {
                // 按照路径深层次访问，如 a.b.c.d.e
                String variablePathStr = Arrays.stream(variablePath).collect(Collectors.joining("."));
                Object nestedProperty = PropertyUtils.getNestedProperty(variable.getValue(), variablePathStr);
                WorkflowVariableType type = WorkflowRuntimeType.getWorkflowType(nestedProperty);
                if (Objects.isNull(type)) {
                    return Optional.empty();
                }

                if (Objects.equals(type, WorkflowVariableType.ARRAY)) {
                    WorkflowVariableType itemType = WorkflowRuntimeType.getItemType(nestedProperty);
                    if (Objects.isNull(itemType)) {
                        return Optional.empty();
                    }

                    parseResult.setType(type).setItemsType(itemType);
                    parseResult.setValue(nestedProperty);
                    return Optional.of(parseResult);
                }
                parseResult.setType(type);
                parseResult.setValue(nestedProperty);
                return Optional.of(parseResult);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<String, IVariable> globalGet(String nodeId) {
        Map<String, IVariable> store = getStore().get(nodeId);
        if (Objects.isNull(store) && Objects.nonNull(parent)) {
            store = parent.globalGet(nodeId);
        }
        return store;
    }

    @Override
    public void init() {
        setStore(new HashMap<>());
    }

    @Override
    public void dispose() {
        getStore().clear();
    }
}
