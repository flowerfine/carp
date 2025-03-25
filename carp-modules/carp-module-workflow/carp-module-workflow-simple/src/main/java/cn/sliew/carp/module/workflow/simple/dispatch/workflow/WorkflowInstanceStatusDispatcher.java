package cn.sliew.carp.module.workflow.simple.dispatch.workflow;

import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.module.workflow.simple.enums.WorkflowInstanceStatus;

public interface WorkflowInstanceStatusDispatcher {

    WorkflowInstanceStatus getStatus();

    void dispatch(DagInstanceDTO dagInstanceDTO);
}
