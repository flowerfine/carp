package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = DefaultFlowTemplateValue.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultFlowConstantValue.class, name = "constant"),
        @JsonSubTypes.Type(value = DefaultFlowRefValue.class, name = "ref"),
        @JsonSubTypes.Type(value = DefaultFlowExpressionValue.class, name = "expression"),
        @JsonSubTypes.Type(value = DefaultFlowTemplateValue.class, name = "template"),
})
public interface IValue {

    String getType();

    Object getContent();
}
