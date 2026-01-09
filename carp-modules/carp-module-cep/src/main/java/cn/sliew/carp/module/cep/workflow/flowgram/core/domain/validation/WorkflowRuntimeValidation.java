package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.validation;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation.IValidation;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IWorkflowSchema;

public class WorkflowRuntimeValidation implements IValidation {

    @Override
    public ValidationResult invoke(IWorkflowSchema schema, WorkflowInputs inputs) {
        // todo validate
        return new ValidationResult().setValid(true);
    }
}
