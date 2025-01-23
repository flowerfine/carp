package cn.sliew.carp.module.orca.spinnaker.orca.core.listeners;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;

public interface StageListener {

    default void beforeTask(StageExecution stage, TaskExecution task) {

    }

    default void beforeStage(StageExecution stage) {

    }

    default void afterTask(StageExecution stage, TaskExecution task) {

    }

    default void afterStage(StageExecution stage) {

    }
}
