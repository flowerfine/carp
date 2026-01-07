package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import java.util.Map;

public interface IJsonSchema {

    String getType();
    Object getDefault();
    String getTitle();
    String getDescription();
    Object[] getEnums();
    Map<String, IJsonSchema> getProperties();
    IJsonSchema getAdditionalProperties();
    IJsonSchema getItems();
    String[] getRequired();
    String getRef();
    Object getExtra();
}
