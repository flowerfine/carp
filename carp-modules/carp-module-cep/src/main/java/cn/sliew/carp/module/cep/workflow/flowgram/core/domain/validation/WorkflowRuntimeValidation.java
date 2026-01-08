package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.validation;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation.IValidation;

public class WorkflowRuntimeValidation implements IValidation {

    @Override
    public ValidationResult invoke(InvokeParams params) {
        // todo validate
        return new ValidationResult().setValid(true);
    }
}
