package cn.sliew.carp.module.workflow.simple.dispatch.workflow.impl;

import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.simple.dispatch.workflow.WorkflowInstanceDispatchEvent;
import cn.sliew.carp.module.workflow.simple.dispatch.workflow.WorkflowInstanceDispatchManager;
import cn.sliew.carp.module.workflow.simple.dispatch.workflow.WorkflowInstanceStatusDispatcher;
import cn.sliew.carp.module.workflow.simple.enums.WorkflowInstanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WorkflowInstanceInitDispatcher implements WorkflowInstanceStatusDispatcher {

    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagLinkService dagLinkService;
    @Autowired
    private WorkflowInstanceDispatchManager workflowInstanceDispatchManager;

    @Override
    public WorkflowInstanceStatus getStatus() {
        return WorkflowInstanceStatus.INIT;
    }

    @Override
    public void dispatch(DagInstanceDTO dagInstanceDTO) {
        List<DagStepDTO> steps = dagStepService.listSteps(dagInstanceDTO.getId());
        steps.forEach(step -> {
            dagStepService.updateStatus(step.getId(), null, WorkflowInstanceStatus.INIT.getValue());
        });

        List<DagLinkDTO> links = dagLinkService.listLinks(dagInstanceDTO.getId());
        links.forEach(link -> {
//            dagLinkService.get(link.getId(), null, WorkflowInstanceStatus.INIT.getValue());
        });

        // 判断是否存在 pre 节点
        workflowInstanceDispatchManager.dispatch(
                WorkflowInstanceDispatchEvent.builder()
                        .workflowInstanceId(dagInstanceDTO.getId())
                        .workflowInstanceStatus(WorkflowInstanceStatus.RUNNING)
                        .build());
    }
}
