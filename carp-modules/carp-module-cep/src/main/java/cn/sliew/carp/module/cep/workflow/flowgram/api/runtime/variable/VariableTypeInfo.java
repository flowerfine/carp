package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VariableTypeInfo {

    private WorkflowVariableType type;
    private WorkflowVariableType itemsType;
}
