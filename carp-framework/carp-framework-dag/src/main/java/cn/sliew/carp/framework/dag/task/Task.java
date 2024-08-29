package cn.sliew.carp.framework.dag.task;

import cn.sliew.carp.framework.dag.task.model.StageExecution;

public interface Task {

    TaskResult execute(StageExecution state);

    TaskResult onTimeout(StageExecution stage);

    void onCancel(StageExecution stage);

}
