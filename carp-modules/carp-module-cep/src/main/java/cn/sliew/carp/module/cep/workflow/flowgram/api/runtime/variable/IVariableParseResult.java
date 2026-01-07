package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IVariableParseResult<T> extends VariableTypeInfo {

    private T value;
}
