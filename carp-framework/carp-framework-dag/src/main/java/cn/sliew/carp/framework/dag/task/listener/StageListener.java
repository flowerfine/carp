package cn.sliew.carp.framework.dag.task.listener;

import cn.sliew.carp.framework.dag.task.model.StageExecution;
import cn.sliew.carp.framework.dag.task.model.TaskExecution;

public interface StageListener {
  default void beforeTask(StageExecution stage, TaskExecution task) {
    // do nothing
  }

  default void beforeStage(StageExecution stage) {
    // do nothing
  }

  default void afterTask(StageExecution stage, TaskExecution task) {
    // do nothing
  }

  default void afterStage(StageExecution stage) {
    // do nothing
  }
}