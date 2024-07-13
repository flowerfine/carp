/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
