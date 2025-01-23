package cn.sliew.carp.module.orca.spinnaker.orca.queue.pending;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InMemoryPendingExecutionService implements PendingExecutionService {

    private final Map<String, Deque<Message>> pending = new ConcurrentHashMap<>();

    @Override
    public void enqueue(String pipelineConfigId, Message message) {
        pendingFor(pipelineConfigId).addLast(message);
    }

    @Override
    public Message popOldest(String pipelineConfigId) {
        Deque<Message> deque = pendingFor(pipelineConfigId);
        if (CollectionUtils.isEmpty(deque)) {
            return null;
        } else {
            return deque.removeFirst();
        }
    }

    @Override
    public Message popNewest(String pipelineConfigId) {
        Deque<Message> deque = pendingFor(pipelineConfigId);
        if (CollectionUtils.isEmpty(deque)) {
            return null;
        } else {
            return deque.removeLast();
        }
    }

    @Override
    public void purge(String pipelineConfigId, Consumer<Message> callback) {
        Deque<Message> deque = pendingFor(pipelineConfigId);
        while (CollectionUtils.isNotEmpty(deque)) {
            callback.accept(deque.removeFirst());
        }
    }

    @Override
    public int depth(String pipelineConfigId) {
        return pendingFor(pipelineConfigId).size();
    }

    @Override
    public List<String> pendingIds() {
        return pending.keySet().stream().toList();
    }

    private Deque<Message> pendingFor(String pipelineConfigId) {
        return pending.computeIfAbsent(pipelineConfigId, key -> new LinkedList<>());
    }
}
