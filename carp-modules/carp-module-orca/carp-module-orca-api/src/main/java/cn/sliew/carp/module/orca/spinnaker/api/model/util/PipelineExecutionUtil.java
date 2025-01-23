package cn.sliew.carp.module.orca.spinnaker.api.model.util;

import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import java.util.List;
import java.util.stream.Collectors;

public enum PipelineExecutionUtil {
    ;

    public static List<StageExecution> initialStages(PipelineExecution execution) {
        return execution.getStages().stream().filter(StageExecutionUtil::isInitial).collect(Collectors.toList());
    }
}
