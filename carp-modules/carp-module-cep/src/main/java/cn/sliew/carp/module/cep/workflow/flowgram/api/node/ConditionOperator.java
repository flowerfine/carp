package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConditionOperator implements DictInstance {

    EQ("eq", "Equal"),
    NEQ("neq", "Not Equal"),
    GT("gt", "Greater Than"),
    GTE("gte", "Greater Than or Equal"),
    LT("lt", "Less Than"),
    LTE("lte", "Less Than or Equal"),
    IN("in", "In"),
    NIN("nin", "Not In"),
    CONTAINS("contains", "Contains"),
    NOT_CONTAINS("not_contains", "Not Contains"),
    IS_EMPTY("is_empty", "Is Empty"),
    IS_NOT_EMPTY("is_not_empty", "Is Not Empty"),
    IS_TRUE("is_true", "Is True"),
    IS_FALSE("is_false", "Is False"),
    ;

    @JsonCreator
    public static ConditionOperator of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(ConditionOperator.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    ConditionOperator(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
