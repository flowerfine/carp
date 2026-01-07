package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IVariable<T> extends VariableTypeInfo {

    private String id;
    private String nodeId;
    private String key;
    private T value;
}
