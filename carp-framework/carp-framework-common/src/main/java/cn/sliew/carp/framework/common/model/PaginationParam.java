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

package cn.sliew.carp.framework.common.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.sliew.milky.common.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页参数")
public class PaginationParam implements Serializable {
    private static final long serialVersionUID = -860020632404225667L;

    @Schema(description = "页码", example = "1")
    private Long current = 1L;

    @Schema(description = "页面大小", example = "10")
    private Long pageSize = 10L;

    @Schema(description = "排序", example = "[{direction: \"ASC\"" + "field: \"fieldName\"}]")
    private List<SortArg> sorter;

    public List<OrderItem> buildSortItems() {
        if (CollectionUtil.isEmpty(sorter)) {
            return Collections.emptyList();
        }

        return sorter.stream()
                .filter(arg -> StringUtils.isNotBlank(arg.getField()))
                .map(arg -> {
                    if ("desc".equalsIgnoreCase(arg.getDirection())) {
                        return OrderItem.desc(StrUtil.toUnderlineCase(arg.getField()));
                    } else if ("asc".equalsIgnoreCase(arg.getDirection())) {
                        return OrderItem.asc(StrUtil.toUnderlineCase(arg.getField()));
                    } else {
                        return OrderItem.asc(StrUtil.toUnderlineCase(arg.getField()));
                    }
                }).collect(Collectors.toList());
    }

    public Long getCurrent() {
        return current == null || current < 1L ? 1L : current;
    }

    public Long getPageSize() {
        return pageSize == null || pageSize < 1L ? 10L : pageSize;
    }

}
