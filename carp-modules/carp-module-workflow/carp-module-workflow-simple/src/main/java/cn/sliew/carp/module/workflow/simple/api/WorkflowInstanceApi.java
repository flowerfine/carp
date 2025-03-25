package cn.sliew.carp.module.workflow.simple.api;

import cn.sliew.carp.module.workflow.simple.api.request.WorkflowInstanceStartParam;

public interface WorkflowInstanceApi {

    Long start(WorkflowInstanceStartParam param);
}
