package cn.sliew.carp.module.cep.workflow.flowgram.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class DefaultJsonSchema implements IJsonSchema {

    private String type;
    @JsonProperty("default")
    private Object defaultValue;
    private String title;
    private String description;
    private List<Object> enums;
    private Map<String, IJsonSchema> properties;
    private IJsonSchema additionalProperties;
    private IJsonSchema items;
    private List<String> required;
    @JsonProperty("$ref")
    private String ref;
    private Map<String, Object> extra;
}
