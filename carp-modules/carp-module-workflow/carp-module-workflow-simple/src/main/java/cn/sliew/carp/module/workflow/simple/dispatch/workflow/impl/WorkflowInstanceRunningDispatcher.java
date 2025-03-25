package cn.sliew.carp.module.workflow.simple.dispatch.workflow.impl;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DagUtil;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.simple.dispatch.workflow.WorkflowInstanceStatusDispatcher;
import cn.sliew.carp.module.workflow.simple.enums.WorkflowInstanceStatus;
import cn.sliew.carp.module.workflow.simple.enums.WorkflowStepInstanceStatus;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkflowInstanceRunningDispatcher implements WorkflowInstanceStatusDispatcher {

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagLinkService dagLinkService;

    @Override
    public WorkflowInstanceStatus getStatus() {
        return WorkflowInstanceStatus.RUNNING;
    }

    @Override
    public void dispatch(DagInstanceDTO dagInstanceDTO) {
        List<DagStepDTO> steps = dagStepService.listSteps(dagInstanceDTO.getId());
        List<DagLinkDTO> links = dagLinkService.listLinks(dagInstanceDTO.getId());

        DAG<DagStepDTO> dag = dagInstanceComplexService.getDagNew(dagInstanceDTO.getId());

        //出度递归，将错误和停止向后传递
        //即如果当前节点 EXCEPTION 或 STOPPED，则循环更新当前节点的后续所有节点，更新为 SKIP_BY_EXCEPTION 或 SKIP_BY_STOPPED
        for (DagStepDTO step : dag.nodes()) {
            broadcastOnExceptionOrStopped(dag, step);
        }

        // 入度递归，如果前置节点已完成，触发当前节点执行
        for (DagStepDTO step : dag.nodes()) {
            runIfAncestorsAllFinished(dag, step);
        }


    }

    private void broadcastOnExceptionOrStopped(DAG<DagStepDTO> dag, DagStepDTO step) {
        WorkflowStepInstanceStatus nodeStatus = WorkflowStepInstanceStatus.of(step.getStatus());
        //非异常和非停止
        if (!nodeStatus.isException() && !nodeStatus.isStopped()) {
            return;
        }

        // children 包含自身
        List<DagStepDTO> children = dag.getChildren(step);
        if (CollectionUtils.isNotEmpty(children)) {
            for (DagStepDTO child : children) {
                // children 包含自身，需跳过自身
                if (Objects.equals(child.getId(), step.getId())) {
                    continue;
                }
                WorkflowStepInstanceStatus childNodeStatus = WorkflowStepInstanceStatus.of(child.getStatus());
                if (nodeStatus.isException()) {
                    if (!childNodeStatus.isException()) {
                        log.info(">>>runningDagInstDispatcher|doChildrenOnException|{}->{}", child.getId(),
                                WorkflowStepInstanceStatus.SKIP_CAUSE_BY_EXCEPTION);
                        dagStepService.updateStatus(child.getId(), null, WorkflowStepInstanceStatus.SKIP_CAUSE_BY_EXCEPTION.getValue());
                    }
                    child.setStatus(WorkflowStepInstanceStatus.SKIP_CAUSE_BY_EXCEPTION.getValue());
                } else if (nodeStatus.isStopped()) {
                    if (!childNodeStatus.isStopped()) {
                        log.info(">>>runningDagInstDispatcher|doChildrenOnStopped|{}->{}", child.getId(),
                                WorkflowStepInstanceStatus.SKIP_CAUSE_BY_EXCEPTION);
                        dagStepService.updateStatus(child.getId(), null, WorkflowStepInstanceStatus.SKIP_CAUSE_BY_STOPPED.getValue());
                    }
                    child.setStatus(WorkflowStepInstanceStatus.SKIP_CAUSE_BY_STOPPED.getValue());
                }

                broadcastOnExceptionOrStopped(dag, child);
            }
        }
    }

    private void runIfAncestorsAllFinished(DAG<DagStepDTO> dag, DagStepDTO step) {
        WorkflowStepInstanceStatus nodeStatus = WorkflowStepInstanceStatus.of(step.getStatus());
        if (Objects.equals(nodeStatus, WorkflowStepInstanceStatus.INIT) == false) {
            return;
        }

        // children 包含自身
        List<DagStepDTO> ancestors = dag.getAncestors(step);
        // ancestors 为空，表面无前置条件，直接运行
        if (ancestors.size() == 1) {
            log.info(">>>runningDagInstDispatcher|dispatch|start|Header Node={}", JacksonUtil.toJsonString(step));
            return;
        }

    }
}
