package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;

import java.util.List;

@Data
public class DefaultFlowRefValue implements IFlowRefValue {

    private String type;
    private List<String> content;
}
