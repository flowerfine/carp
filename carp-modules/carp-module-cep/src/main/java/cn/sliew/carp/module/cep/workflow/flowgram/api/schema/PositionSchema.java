package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PositionSchema {

    private Integer x;
    private Integer y;
}
