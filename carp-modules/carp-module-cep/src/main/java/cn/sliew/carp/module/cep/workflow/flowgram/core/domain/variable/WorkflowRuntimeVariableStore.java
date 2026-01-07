package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.variable;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariable;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
            IVariable variable = WorkflowRuntimeVariable.create(null);
            nodeStore.put(param.getVariableKey(), variable);
        }

        IVariable variable = nodeStore.get(param.getVariableKey());
        if (ArrayUtils.isEmpty(param.getVariablePath())) {
            variable.setValue(param.getValue());
        } else {
            // fixme 实现错误，应该是至复制 variablePath 对应的字段，这里变成了忽略
            BeanUtil.copyProperties(param.getValue(), variable.getValue(), param.getVariablePath());
        }
    }

    @Override
    public Optional<IVariableParseResult> getValue(GetValueParam param) {
        Map<String, IVariable> nodeStore = globalGet(param.getNodeID());
        if (Objects.isNull(nodeStore)) {
            return Optional.empty();
        }
        IVariable variable = nodeStore.get(param.getVariableKey());
        if (Objects.isNull(variable)) {
            return Optional.empty();
        }

        IVariableParseResult parseResult = new IVariableParseResult();
        if (ArrayUtils.isEmpty(param.getVariablePath())) {
            parseResult
                    .setValue(variable.getValue())
                    .setType(variable.getType())
                    .setItemsType(variable.getItemsType());
        } else {
            // fixme todo
        }
        return Optional.of(parseResult);
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
