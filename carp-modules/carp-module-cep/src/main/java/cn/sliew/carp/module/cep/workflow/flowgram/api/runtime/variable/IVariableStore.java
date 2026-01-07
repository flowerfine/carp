package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;

@Data
public abstract class IVariableStore {

    private String id;
    private Map<String, Map<String, IVariable>> store;

    public abstract void setParent(IVariableStore parent);

    public abstract void setVariable(SetVariableParam param);

    public abstract void setValue(SetValueParam param);

    public abstract Optional<IVariableParseResult> getValue(GetValueParam param);

    public abstract void init();

    public abstract void dispose();

    @Data
    @Accessors(chain = true)
    public static class SetVariableParam extends VariableTypeInfo {
        private String nodeID;
        private String key;
        private Object value;
    }

    @Data
    public static class SetValueParam extends VariableTypeInfo {
        private String nodeID;
        private String variableKey;
        private String[] variablePath;
        private Object value;
    }

    @Data
    public static class GetValueParam extends VariableTypeInfo {
        private String nodeID;
        private String variableKey;
        private String[] variablePath;
    }

}
