package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowVariableType implements DictInstance {

    STRING("string", "String"),
    INTEGER("integer", "Integer"),
    NUMBER("number", "Number"),
    BOOLEAN("boolean", "Boolean"),
    OBJECT("object", "Object"),
    ARRAY("array", "Array"),
    MAP("map", "Map"),
    DATE_TIME("date-time", "DateTime"),
    NULL("null", "Null"),
    ;

    @JsonCreator
    public static WorkflowVariableType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(WorkflowVariableType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    WorkflowVariableType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
