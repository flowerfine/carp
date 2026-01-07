package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class FlattenData {

    private WorkflowSchema flattenSchema;
    private Map<String, List<String>> nodeBlocks;
    private Map<String, List<String>> nodeEdges;
}
