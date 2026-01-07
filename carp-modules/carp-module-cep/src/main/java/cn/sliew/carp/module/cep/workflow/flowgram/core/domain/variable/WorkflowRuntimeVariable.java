package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.variable;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariable;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;

public class WorkflowRuntimeVariable {

    public static IVariable create(IVariableStore.SetVariableParam param) {
        IVariable variable = new IVariable();
        variable.setId(UUIDUtil.randomUUId())
                .setNodeId(param.getNodeID())
                .setKey(param.getKey())
                .setValue(param.getValue())
                .setType(param.getType())
                .setItemsType(param.getItemsType());
        return variable;
    }
}
