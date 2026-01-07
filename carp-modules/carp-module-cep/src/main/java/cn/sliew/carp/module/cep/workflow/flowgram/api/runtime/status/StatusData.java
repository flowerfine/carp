package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatusData {

    private WorkflowStatus status;
    private Boolean terminated;
    private Long startTime;
    private Long endTime;
    private Long timeCost;
}
