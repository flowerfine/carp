package cn.sliew.carp.module.orca.spinnaker.orca.queue.pending;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;

import java.util.List;
import java.util.function.Consumer;

public interface PendingExecutionService {

    void enqueue(String pipelineConfigId, Message message);

    Message popOldest(String pipelineConfigId);

    Message popNewest(String pipelineConfigId);

    void purge(String pipelineConfigId, Consumer<Message> callback);

    int depth(String pipelineConfigId);

    List<String> pendingIds();
}
