package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.document.document;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowSchema;

import java.util.function.Function;

public interface FlatSchema extends Function<WorkflowSchema, FlattenData> {
}
