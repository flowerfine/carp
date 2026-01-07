package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JsonSchemaBasicType implements DictInstance {

    BOOLEAN("boolean", "boolean"),
    STRING("string", "string"),
    INTEGER("integer", "integer"),
    NUMBER("number", "number"),
    OBJECT("object", "object"),
    ARRAY("array", "array"),
    MAP("map", "map"),
    ;

    @JsonCreator
    public static JsonSchemaBasicType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(JsonSchemaBasicType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    JsonSchemaBasicType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
