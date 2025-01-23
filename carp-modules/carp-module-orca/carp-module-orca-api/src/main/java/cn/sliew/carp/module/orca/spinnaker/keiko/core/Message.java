package cn.sliew.carp.module.orca.spinnaker.keiko.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = Message.JSON_NAME_PROPERTY)
public abstract class Message {

    public static final String JSON_NAME_PROPERTY = "kind";

    public static final String ATTRIBUTE_MAX_ATTEMPTS = "maxAttempts";
    public static final Integer ATTRIBUTE_MAX_ATTEMPTS_VALUE = -1;
    public static final String ATTRIBUTE_ATTEMPTS = "attempts";
    public static final Integer ATTRIBUTE_ATTEMPTS_VALUE = 0;
    public static final String ATTRIBUTE_ACK_ATTEMPTS = "ackAttempts";
    public static final Integer ATTRIBUTE_ACK_ATTEMPTS_VALUE = 0;

    private Map<String, Object> attributes = Maps.newHashMap();

    @Getter
    private Long ackTimeoutMs = null;

    public Object getAttribute(String name, Object defaultValue) {
        return attributes.getOrDefault(name, defaultValue);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

}
