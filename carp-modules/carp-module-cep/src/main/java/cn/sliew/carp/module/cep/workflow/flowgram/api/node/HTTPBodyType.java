package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HTTPBodyType implements DictInstance {

    NONE("none", "None"),
    FORM_DATA("form-data", "FormData"),
    X_WWW_FORM_URLENCODED("x-www-form-urlencoded", "XWwwFormUrlencoded"),
    RAW_TEXT("raw-text", "RawText"),
    JSON("JSON", "JSON"),
    BINARY("binary", "Binary"),
    ;

    @JsonCreator
    public static HTTPBodyType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(HTTPBodyType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    HTTPBodyType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
