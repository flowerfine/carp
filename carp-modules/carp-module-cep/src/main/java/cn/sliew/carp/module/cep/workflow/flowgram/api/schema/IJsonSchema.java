package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = DefaultJsonSchema.class)
public interface IJsonSchema {

    String getType();
    Object getDefaultValue();
    String getTitle();
    String getDescription();
    List<Object> getEnums();
    Map<String, IJsonSchema> getProperties();
    IJsonSchema getAdditionalProperties();
    IJsonSchema getItems();
    List<String> getRequired();
    String getRef();
    Map<String, Object> getExtra();
}
