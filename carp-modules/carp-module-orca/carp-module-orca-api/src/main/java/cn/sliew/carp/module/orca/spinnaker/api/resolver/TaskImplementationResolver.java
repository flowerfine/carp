package cn.sliew.carp.module.orca.spinnaker.api.resolver;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.TaskNode;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

/** Resolves the task implementation for a given task node. */
public interface TaskImplementationResolver {

  default TaskNode.DefinedTask resolve(StageExecution stage, TaskNode.DefinedTask taskNode) {
    return taskNode;
  }
}
