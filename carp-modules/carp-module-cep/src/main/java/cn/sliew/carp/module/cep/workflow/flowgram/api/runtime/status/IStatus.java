package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status;

import lombok.Data;

@Data
public abstract class IStatus extends StatusData {

    private String id;

    public abstract void process();

    public abstract void success();

    public abstract void fail();

    public abstract void cancel();

    public abstract StatusData export();
}
