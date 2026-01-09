package cn.sliew.carp.module.cep.workflow.flowgram.api.node;

import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowConstantRefValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowTemplateValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IJsonSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IValue;
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
        private Map<String, IValue> headersValues;
        private IJsonSchema params;
        private Map<String, IValue> paramsValues;
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
        private IFlowTemplateValue json;
        private IJsonSchema formData;
        private Map<String, IValue> formDataValues;
        private IFlowTemplateValue rawText;
        private IFlowTemplateValue binary;
        private IJsonSchema xWwwFormUrlencoded;
        private Map<String, IValue> xWwwFormUrlencodedValues;
    }

    @Data
    public static class TimeoutData {
        private Long retryTimes;
        private Long timeout;
    }

}
