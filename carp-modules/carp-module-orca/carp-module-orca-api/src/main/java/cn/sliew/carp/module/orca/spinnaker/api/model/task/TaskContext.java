package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

public interface TaskContext {

    ImmutableMap<String, Object> getInputs();

    default ImmutableMap<String, Object> getInputs(String prefix) {
        final String prefixWithDot = format("%s.", prefix);

        return ImmutableMap.copyOf(
                getInputs().entrySet().stream()
                        .filter(it -> it.getKey().startsWith(prefixWithDot))
                        .collect(
                                toMap(it -> it.getKey().substring(prefixWithDot.length()), Map.Entry::getValue)));
    }
}
