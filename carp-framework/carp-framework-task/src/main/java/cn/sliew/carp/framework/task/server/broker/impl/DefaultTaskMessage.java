package cn.sliew.carp.framework.task.server.broker.impl;

import cn.sliew.carp.framework.task.server.broker.TaskMessage;
import cn.sliew.carp.framework.task.server.detail.TaskDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultTaskMessage implements TaskMessage {

    private String id;
    private Integer status;

    private String topic;
    private TaskDetail taskDetail;
    private Long produceTime;
    private Long triggerTime;
}
