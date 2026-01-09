package cn.sliew.carp.module.cep.workflow.flowgram.core.application;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.container.IContainer;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine.IEngine;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter.IReport;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.task.ITask;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation.IValidation;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.container.WorkflowRuntimeContainer;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WorkflowApplication {

    private IContainer container;
    private Map<String, ITask> tasks;

    public WorkflowApplication() {
        this.container = WorkflowRuntimeContainer.instance();
        this.tasks = new HashMap<>();
    }

    public String invoke(InvokeParams params) {
        IEngine engine = (IEngine) container.get("IEngine");
        ITask task = engine.invoke(params.getSchema(), params.getInputs());
        tasks.put(task.getId(), task);
        log.info("> POST TaskRun - taskID: {}, inputs: {}", task.getId(), JacksonUtil.toJsonString(params.getInputs()));
        task.getProcessing().whenComplete((outputs, throwable) -> {
            log.info("> LOG Task finished: {}, outputs: {}", task.getId(), JacksonUtil.toJsonString(outputs));
        });
        return task.getId();
    }

    public boolean cancel(String taskId) {
        log.info("> PUT TaskCancel - taskID: {}", taskId);
        if (tasks.containsKey(taskId)) {
            tasks.get(taskId).cancel();
            return true;
        }
        return false;
    }

    public IReport report(String taskId) {
        log.info("> GET TaskReport - taskID: {}", taskId);
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId).getContext().getReporter().export();
        }
        return null;
    }

    public IValidation.ValidationResult validate(InvokeParams params) {
        IValidation validation = (IValidation) container.get("IValidation");
        IValidation.ValidationResult validationResult = validation.invoke(params.getSchema(), params.getInputs());
        log.info("> POST TaskValidate - valid: {}", validationResult.isValid());
        return validationResult;
    }
}
