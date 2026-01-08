package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DefaultFlowRefValue implements IFlowRefValue {

    private String type;
    private List<String> content;
}
