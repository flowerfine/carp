package cn.sliew.carp.framework.dag.task.pipeline.model;

import cn.sliew.carp.framework.dag.task.model.StageExecution;
import com.google.common.collect.ForwardingMap;
import jakarta.annotation.Nullable;
import org.springframework.scheduling.Trigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class StageContext extends ForwardingMap<String, Object> implements Map<String, Object> {

  private final StageExecution stage;
  private final Map<String, Object> delegate;

  public StageContext(StageExecution stage) {
    this(stage, new HashMap<>());
  }

  public StageContext(StageContext stageContext) {
    this(stageContext.stage, new HashMap<>(stageContext.delegate));
  }

  public StageContext(StageExecution stage, Map<String, Object> delegate) {
    this.stage = stage;
    this.delegate = delegate;
  }

  @Override
  protected Map<String, Object> delegate() {
    return delegate;
  }

  private Trigger getTrigger() {
    return stage.getExecution().getTrigger();
  }

  @Override
  public Object get(@Nullable Object key) {
    if (delegate().containsKey(key)) {
      return super.get(key);
    } else {
      return stage.ancestors().stream()
          .filter(it -> it.getOutputs().containsKey(key))
          .findFirst()
          .map(it -> it.getOutputs().get(key))
          .orElse(null);
    }
  }

  /**
   * Get a value from the current context ONLY - never looking at the ancestors' outputs
   *
   * @param key The key to look
   * @param defaultValue default value to return if key is not present
   * @return value or null if not present
   */
  public Object getCurrentOnly(@Nullable Object key, Object defaultValue) {
    return super.getOrDefault(key, defaultValue);
  }
  /*
   * Gets all objects matching 'key', sorted by proximity to the current stage.
   * If the key exists in the current context, it will be the first element returned
   */
  @SuppressWarnings("unchecked")
  public <E> List<E> getAll(Object key) {
    List<E> result =
        (List<E>)
            stage.ancestors().stream()
                .filter(it -> it.getOutputs().containsKey(key))
                .map(it -> it.getOutputs().get(key))
                .collect(toList());

    if (delegate.containsKey(key)) {
      result.add(0, (E) delegate.get(key));
    }

    if (key.equals("artifacts")) {
      result.add((E) getTrigger().getArtifacts());
    } else if (key.equals("resolvedExpectedArtifacts")) {
      result.add((E) getTrigger().getResolvedExpectedArtifacts());
    }

    return result;
  }
}
