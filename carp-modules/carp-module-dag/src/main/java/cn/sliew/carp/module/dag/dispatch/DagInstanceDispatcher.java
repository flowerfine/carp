package cn.sliew.carp.module.dag.dispatch;

import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;

public interface DagInstanceDispatcher {

    void dispatch(DagInstanceDTO event);
}
