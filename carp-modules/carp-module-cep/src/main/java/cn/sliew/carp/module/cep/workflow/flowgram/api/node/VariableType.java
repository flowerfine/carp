package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VariableType implements DictInstance {

    DECLARATION("declare", "Declaration"),
    ASSIGN("assign", "Assign"),
    ;

    @JsonCreator
    public static VariableType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(VariableType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    VariableType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
