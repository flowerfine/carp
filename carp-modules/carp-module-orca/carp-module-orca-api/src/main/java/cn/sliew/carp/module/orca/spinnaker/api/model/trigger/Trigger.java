package cn.sliew.carp.module.orca.spinnaker.api.model.trigger;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class Trigger {

    private String type;
    private String correlationId;
    @Builder.Default
    private Map<String, Object> parameters = Maps.newHashMap();
}
