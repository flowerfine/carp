package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.reporter;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.Snapshot;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.StatusData;
import lombok.Data;

import java.util.List;

@Data
public class NodeReport extends StatusData {

    private String id;
    private List<Snapshot> snapshots;
}
