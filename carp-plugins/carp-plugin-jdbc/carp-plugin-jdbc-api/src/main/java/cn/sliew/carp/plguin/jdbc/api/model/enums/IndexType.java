/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.carp.plguin.jdbc.api.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum IndexType {
    TABLE_INDEX_STATISTIC(0),
    TABLE_INDEX_CLUSTERED(1),
    TABLE_INDEX_HASHED(2),
    TABLE_INDEX_OTHER(3);

    private static final Map<Integer, IndexType> INDEX_TYPE_MAP = Arrays.stream(IndexType.values())
            .collect(Collectors.toMap(IndexType::getIndexType, f -> f));

    private final int indexType;

    IndexType(int indexType) {
        this.indexType = indexType;
    }

    public static IndexType fromIndexType(int indexType) {
        if (INDEX_TYPE_MAP.containsKey(indexType)) {
            return INDEX_TYPE_MAP.get(indexType);
        } else {
            throw new IllegalArgumentException("Unknown type: " + indexType);
        }
    }

}
