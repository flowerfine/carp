/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
