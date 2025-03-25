package cn.sliew.carp.module.workflow.simple.dispatch.workflow;

import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class LocalWorkflowInstanceDispatchManager implements WorkflowInstanceDispatchManager {

    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private List<WorkflowInstanceStatusDispatcher> handlerList;

    @Async
    @Override
    public void dispatch(WorkflowInstanceDispatchEvent event) {
        WorkflowInstanceStatusDispatcher workflowInstanceStatusDispatcher = handlerList.stream().filter(
                        handler -> handler.getStatus() == event.getWorkflowInstanceStatus())
                .findFirst().orElse(null);

        if (Objects.nonNull(workflowInstanceStatusDispatcher)) {
            DagInstanceDTO dagInstanceDTO = dagInstanceService.getWithConfig(event.getWorkflowInstanceId());
            workflowInstanceStatusDispatcher.dispatch(dagInstanceDTO);
        }
    }
}
