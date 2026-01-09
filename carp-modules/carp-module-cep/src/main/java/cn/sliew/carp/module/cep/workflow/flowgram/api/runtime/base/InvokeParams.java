package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;
import lombok.Data;

/**
 * todo 干掉这玩意，略烦人，内部的接口干掉
 */
@Data
public class InvokeParams {

    private IWorkflowSchema schema;
    private WorkflowInputs inputs;
}
