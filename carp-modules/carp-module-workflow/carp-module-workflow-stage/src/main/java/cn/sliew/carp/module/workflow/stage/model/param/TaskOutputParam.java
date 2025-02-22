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
package cn.sliew.carp.module.workflow.stage.model.param;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskOutputParam extends TaskParam {

    private String name;
    private String alias;
    private ParamDataType type;

    public static List<TaskOutputParam> toList(JsonNode outputParamArray) {
        List<TaskOutputParam> outputParamList = new ArrayList<>();
        if (outputParamArray.isArray()) {
            ArrayNode arrayNode = (ArrayNode) outputParamArray;
            for (JsonNode jsonNode : arrayNode) {
                String name = jsonNode.path("name").asText();
                String alias = jsonNode.path("alias").asText();
                outputParamList.add(
                        TaskOutputParam.builder()
                                .name(name)
                                .alias(StringUtils.isEmpty(alias) ? name : alias)
                                .type(ParamDataType.valueOf(jsonNode.path("type").asText().toUpperCase()))
                                .build());
            }
        }
        return outputParamList;
    }

}
