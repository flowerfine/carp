package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

public interface IValidation {

    ValidationResult invoke(InvokeParams params);

    @Data
    @Accessors(chain = true)
    class ValidationResult {

        private boolean valid;
        private List<String> errors;
    }
}
