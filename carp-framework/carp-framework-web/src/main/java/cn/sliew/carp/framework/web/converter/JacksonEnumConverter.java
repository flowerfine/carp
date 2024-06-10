package cn.sliew.carp.framework.web.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JacksonEnumConverter implements GenericConverter {

    private Set<ConvertiblePair> set;
    private ObjectMapper mapper;

    public JacksonEnumConverter(ObjectMapper mapper) {
        set = new HashSet<>();
        set.add(new ConvertiblePair(String.class, Enum.class));
        this.mapper = mapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return set;
    }

    @Override
    public Object convert(Object value, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (value == null) {
            return null;
        }
        try {
            return mapper.readValue("\"" + value + "\"", targetType.getType());
        } catch (IOException e) {
            throw new ConversionFailedException(sourceType, targetType, value, e);
        }
    }
}
