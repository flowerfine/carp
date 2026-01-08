package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowStatus implements DictInstance {

    PENDING("pending", "Pending"),
    PROCESSING("processing", "Processing"),
    SUCCEEDED("succeeded", "Succeeded"),
    FAILED("failed", "Failed"),
    CANCELLED("canceled", "Cancelled"),
    ;

    @JsonCreator
    public static WorkflowStatus of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(WorkflowStatus.class, value));
    }

    @JsonValue
    @EnumValue
    private String value;
    private String label;

    WorkflowStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
