package cn.sliew.carp.module.workflow.simple.api.impl;

import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.module.workflow.simple.api.WorkflowInstanceApi;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowInstanceStartParam;
import cn.sliew.carp.module.workflow.simple.dispatch.workflow.WorkflowInstanceDispatchEvent;
import cn.sliew.carp.module.workflow.simple.dispatch.workflow.WorkflowInstanceDispatchManager;
import cn.sliew.carp.module.workflow.simple.enums.WorkflowInstanceStatus;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WorkflowInstanceApiImpl implements WorkflowInstanceApi {

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private WorkflowInstanceDispatchManager workflowInstanceDispatchManager;

    @Override
    public Long start(WorkflowInstanceStartParam param) {
        JsonNode globalVariable = param.getGlobalVariable();
        if (Objects.isNull(globalVariable)) {
            globalVariable = JacksonUtil.createObjectNode();
        }

        Long dagInstanceId = dagInstanceComplexService.initialize(param.getWorkflowDefinitionId());

        DagInstanceDTO dagInstanceDTO = new DagInstanceDTO();
        dagInstanceDTO.setId(dagInstanceId);
        ObjectNode body = JacksonUtil.createObjectNode();
        body.putPOJO("globalVariable", globalVariable);
        dagInstanceDTO.setInputs(JacksonUtil.createObjectNode());
        dagInstanceDTO.setStatus(WorkflowInstanceStatus.INIT.getValue());
        dagInstanceService.update(dagInstanceDTO);

        workflowInstanceDispatchManager.dispatch(
                WorkflowInstanceDispatchEvent.builder()
                        .workflowInstanceId(dagInstanceId)
                        .workflowInstanceStatus(WorkflowInstanceStatus.INIT)
                        .build());

        return dagInstanceId;
    }
}
