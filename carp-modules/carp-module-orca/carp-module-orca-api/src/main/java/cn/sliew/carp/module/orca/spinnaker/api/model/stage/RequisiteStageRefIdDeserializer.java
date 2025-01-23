package cn.sliew.carp.module.orca.spinnaker.api.model.stage;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Collection;

public class RequisiteStageRefIdDeserializer extends StdDeserializer<Collection<String>> {

    public RequisiteStageRefIdDeserializer() {
        super(Collection.class);
    }

    @Override
    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return jsonParser.getCodec().readValue(jsonParser, new TypeReference<Collection<String>>() {
        });
    }
}
