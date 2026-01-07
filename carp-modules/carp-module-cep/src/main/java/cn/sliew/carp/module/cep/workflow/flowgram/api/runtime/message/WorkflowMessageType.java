package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.message;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowMessageType implements DictInstance {

    LOG("log", "Log"),
    INFO("info", "Info"),
    DEBUG("debug", "Debug"),
    ERROR("error", "Error"),
    WARN("warning", "Warn"),
    ;

    @JsonCreator
    public static WorkflowMessageType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(WorkflowMessageType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    WorkflowMessageType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
