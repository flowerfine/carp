package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.IExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.validation.IValidation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EngineServices {

    private IValidation validation;
    private IExecutor executor;
}
