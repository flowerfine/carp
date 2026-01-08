package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowConstantRefValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowTemplateValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IJsonSchema;
import lombok.Data;

import java.util.Map;

@Data
public class HTTPNodeSchema extends AbstractWorkflowNodeSchema<HTTPNodeSchema.HTTPNodeData> {

    private HTTPNodeData data;

    @Override
    public NodeType getType() {
        return NodeType.HTTP;
    }

    @Override
    public HTTPNodeData getData() {
        return data;
    }

    @Data
    public static class HTTPNodeData extends AbstractWorkflowNodeData {
        private ApiData api;
        private IJsonSchema headers;
        private Map<String, IFlowConstantRefValue> headersValues;
        private IJsonSchema params;
        private Map<String, IFlowConstantRefValue> paramsValues;
        private BodyData body;
        private TimeoutData timeout;
    }

    @Data
    public static class ApiData {
        private String method;
        private IFlowTemplateValue url;
    }

    @Data
    public static class BodyData {
        private String bodyType;
        // todo
    }

    @Data
    public static class TimeoutData {
        private Long retryTimes;
        private Long timeout;
    }

}
