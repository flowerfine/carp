package cn.sliew.carp.module.orca.spinnaker.api.model.jackson;

import cn.sliew.carp.module.orca.spinnaker.api.model.jackson.mixin.PipelineExecutionMixin;
import cn.sliew.carp.module.orca.spinnaker.api.model.jackson.mixin.StageExecutionMixin;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class OrcaModule extends SimpleModule {

    public OrcaModule() {
        super("apiTypes", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(TaskExecution.class, TaskExecutionImpl.class);
        resolver.addMapping(StageExecution.class, StageExecutionImpl.class);
        resolver.addMapping(PipelineExecution.class, PipelineExecutionImpl.class);
        setMixInAnnotation(StageExecution.class, StageExecutionMixin.class);
        setMixInAnnotation(PipelineExecution.class, PipelineExecutionMixin.class);
        setAbstractTypes(resolver);
    }
}
