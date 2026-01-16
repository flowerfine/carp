package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NodeType implements DictInstance {

    ROOT("root", "Root"),
    START("start", "Start"),
    END("end", "End"),
    LLM("llm", "LLM"),
    CODE("code", "Code"),
    CONDITION("condition", "Condition"),
    MULTI_CONDITION("multi-condition", "MultiCondition"),
    LOOP("loop", "Loop"),
    BREAK("break", "Break"),
    CONTINUE("continue", "Continue"),
    COMMENT("comment", "Comment"),
    GROUP("group", "Group"),
    BLOCK_START("block-start", "BlockStart"),
    BLOCK_END("block-end", "BlockEnd"),
    HTTP("http", "HTTP"),
    VARIABLE("variable", "Variable"),
    ;

    @JsonCreator
    public static NodeType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(NodeType.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    NodeType(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
