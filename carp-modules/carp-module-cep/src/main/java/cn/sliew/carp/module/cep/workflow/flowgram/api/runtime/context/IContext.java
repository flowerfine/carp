package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.InvokeParams;
import lombok.Data;

@Data
public abstract class IContext extends ContextData {

    private String id;

    public abstract void init(InvokeParams params);

    public abstract void dispose();

    public abstract IContext sub();
}
