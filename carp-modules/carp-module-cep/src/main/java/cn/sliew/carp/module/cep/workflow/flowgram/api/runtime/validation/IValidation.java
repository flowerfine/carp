package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import lombok.Data;

import java.util.List;

@Data
public abstract class IValidation {

    public abstract ValidationResult invoke(InvokeParams params);

    @Data
    public static class ValidationResult {

        private boolean valid;
        private List<String> errors;
    }
}
