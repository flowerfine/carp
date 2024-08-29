package cn.sliew.carp.framework.dag.task.model;

public enum ExecutionType {
  /** Executions will show under the "Pipelines" tab of Deck. */
  PIPELINE,

  /** Executions will show under the "Tasks" tab of Deck. */
  ORCHESTRATION;
}