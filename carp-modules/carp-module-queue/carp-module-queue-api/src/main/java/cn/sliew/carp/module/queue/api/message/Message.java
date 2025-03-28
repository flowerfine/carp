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
package cn.sliew.carp.module.queue.api.message;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public final class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -3841750464389976750L;

    @Builder.Default
    private String id = UUIDUtil.randomUUId();
    private String topic;
    private byte[] body;
    @Singular
    private Map<String, Object> headers;
    @Singular
    private final List<Attribute> attributes;

    @SuppressWarnings("unchecked")
    public <A extends Attribute> A getAttribute(Class<A> attributeClass) {
        return (A) attributes.stream()
                .filter(attributeClass::isInstance)
                .findFirst()
                .orElse(null);
    }

    public <A extends Attribute> A setAttribute(A attribute) {
        attributes.removeIf(attr -> attr.getClass().equals(attribute.getClass()));
        attributes.add(attribute);
        return attribute;
    }
}
