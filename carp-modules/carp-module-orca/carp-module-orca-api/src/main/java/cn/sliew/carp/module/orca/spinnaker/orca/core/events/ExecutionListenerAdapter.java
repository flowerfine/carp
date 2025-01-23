package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.orca.core.listeners.DefaultPersister;
import cn.sliew.carp.module.orca.spinnaker.orca.core.listeners.ExecutionListener;
import cn.sliew.carp.module.orca.spinnaker.orca.core.listeners.Persister;
import org.springframework.context.ApplicationListener;

/** Adapts events emitted by the nu-orca queue to an old-style listener. */
public final class ExecutionListenerAdapter implements ApplicationListener<ExecutionEvent> {

  private final ExecutionListener delegate;
  private final Persister persister;

  public ExecutionListenerAdapter(ExecutionListener delegate, ExecutionRepository repository) {
    this.delegate = delegate;
    persister = new DefaultPersister(repository);
  }

  @Override
  public void onApplicationEvent(ExecutionEvent event) {
    try {
//      MDC.put(Header.EXECUTION_ID.getHeader(), event.getExecutionId());
      if (event instanceof ExecutionStarted) {
        onExecutionStarted((ExecutionStarted) event);
      } else if (event instanceof ExecutionComplete) {
        onExecutionComplete((ExecutionComplete) event);
      }
    } finally {
//      MDC.remove(Header.EXECUTION_ID.getHeader());
    }
  }

  private void onExecutionStarted(ExecutionStarted event) {
    delegate.beforeExecution(persister, event.getExecution());
  }

  private void onExecutionComplete(ExecutionComplete event) {
    delegate.afterExecution(
        persister, event.getExecution(), event.getStatus(), event.getStatus().isSuccessful());
  }
}
