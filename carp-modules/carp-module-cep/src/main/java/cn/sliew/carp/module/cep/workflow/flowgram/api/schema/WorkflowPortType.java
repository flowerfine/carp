package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowPortType implements DictInstance {

    INPUT("input", "Input"),
    OUTPUT("output", "Output"),
    ;

    @JsonCreator
    public static WorkflowPortType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(WorkflowPortType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    WorkflowPortType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
