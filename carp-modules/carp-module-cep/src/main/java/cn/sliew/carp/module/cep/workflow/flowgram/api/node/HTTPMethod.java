package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HTTPMethod implements DictInstance {

    GET("GET", "GET"),
    POST("POST", "POST"),
    PUT("PUT", "PUT"),
    DELETE("DELETE", "DELETE"),
    PATCH("PATCH", "PATCH"),
    HEAD("HEAD", "HEAD"),
    ;

    @JsonCreator
    public static HTTPMethod of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(HTTPMethod.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    HTTPMethod(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
