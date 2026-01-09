package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.container;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.container.IContainer;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine.EngineServices;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.engine.WorkflowRuntimeEngine;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.executor.WorkflowRuntimeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.validation.WorkflowRuntimeValidation;
import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.WorkflowRuntimeNodeExecutors;

import java.util.HashMap;
import java.util.Map;

public class WorkflowRuntimeContainer implements IContainer {

    public static final String VALIDATION = "IValidation";
    public static final String EXECUTOR = "IExecutor";
    public static final String ENGINE = "IEngine";

    private static final WorkflowRuntimeContainer INSTANCE = create();

    private Map<String, Object> services;

    public WorkflowRuntimeContainer(Map<String, Object> services) {
        this.services = services;
    }

    @Override
    public Object get(Object key) {
        return services.get(key);
    }

    private static WorkflowRuntimeContainer create() {
        WorkflowRuntimeValidation validation = new WorkflowRuntimeValidation();
        WorkflowRuntimeExecutor executor = new WorkflowRuntimeExecutor(WorkflowRuntimeNodeExecutors.NODE_EXECUTORS);
        WorkflowRuntimeEngine engine = new WorkflowRuntimeEngine(new EngineServices()
                .setValidation(validation)
                .setExecutor(executor)
        );
        Map<String, Object> map = new HashMap<>();
        map.put(VALIDATION, validation);
        map.put(EXECUTOR, executor);
        map.put(ENGINE, engine);
        return new WorkflowRuntimeContainer(map);
    }

    public static WorkflowRuntimeContainer instance() {
        return INSTANCE;
    }
}
