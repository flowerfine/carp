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
package cn.sliew.carp.module.workflow.stage.model.domain.param;

import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;
import lombok.Builder.Default;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskInputParam extends TaskParam {

    public static final String OUTPUT_DATA_OUTPUT_KEY = "output";

    private String name;
    private String alias;
    private Object value;
    private ParamDataType type;
    private Boolean isOverWrite;

    @Default
    private ParamFromType fromType = ParamFromType.CONSTANT;
    private String from;

    public StepInputParam toStepInputParam() {
        return StepInputParam.builder()
                .name(name)
                .alias(alias)
                .value(value)
                .type(type)
                .isOverWrite(isOverWrite)
                .build();
    }

    public void setValue(JsonNode jsonObject) {
        String name = (String) this.value;
        if (StringUtils.isEmpty(name)) {
            name = this.name;
        }
        switch (type) {
            case STRING:
                value = jsonObject.path(name).asText();
                break;
            case LONG:
                value = jsonObject.path(name).asLong();
                break;
            case DOUBLE:
                value = jsonObject.path(name).asDouble();
                break;
            case BOOLEAN:
                value = jsonObject.path(name).asBoolean();
                break;
            case OBJECT:
                value = jsonObject.path(name);
                break;
            case ARRAY:
                value = jsonObject.path(name);
                break;
            default:
                value = null;
                break;
        }
    }

    public static Map<String, Object> toMap(List<TaskInputParam> inputParamList) {
        Map<String, Object> map = new HashMap<>(0);
        for (TaskInputParam inputParam : inputParamList) {
            map.put(inputParam.getName(), inputParam.getValue());
        }
        return map;
    }

    public static JsonNode toJson(List<TaskInputParam> inputParamList) {
        return JacksonUtil.toJsonNode(toMap(inputParamList));
    }

    public static void setValue(List<TaskInputParam> inputParamList,
                                JsonNode globalVariableJson,
                                JsonNode globalResultJson) {

        for (TaskInputParam inputParam : inputParamList) {
            switch (inputParam.getFromType()) {
                case PARENT:
                    JsonNode jsonObject = globalResultJson.path(inputParam.getFrom());
                    jsonObject = jsonObject.path(OUTPUT_DATA_OUTPUT_KEY);
                    inputParam.setValue(jsonObject);
                    break;
                case GLOBAL_VARIABLE:
                    inputParam.setValue(globalVariableJson);
                    break;
                case CONSTANT:
                default:
                    break;
            }
        }

    }

    public static List<TaskInputParam> toList(JsonNode inputParamArray) {
        List<TaskInputParam> inputParamList = new ArrayList<>();
        if (inputParamArray.isArray()) {
            ArrayNode arrayNode = (ArrayNode) inputParamArray;
            for (JsonNode jsonNode : arrayNode) {
                String name = jsonNode.path("name").asText();
                String alias = jsonNode.path("alias").asText();
                Boolean isOverWrite = jsonNode.path("isOverWrite").asBoolean();
                String type = null;
                if (jsonNode.path("type").isNull() == false) {
                    type = jsonNode.path("type").asText();
                } else {
                    type = "STRING";
                }
                inputParamList.add(
                        TaskInputParam.builder()
                                .name(name)
                                .alias(StringUtils.isEmpty(alias) ? name : alias)
                                .value(jsonNode.path("value").asText())
                                .type(ParamDataType.valueOf(type.toUpperCase()))
                                .isOverWrite(isOverWrite == null ? true : isOverWrite)
                                .from(jsonNode.path("from").asText())
                                .fromType(jsonNode.path("fromType").isNull() ?
                                        ParamFromType.CONSTANT : ParamFromType.valueOf(jsonNode.path("fromType").asText()))
                                .build());
            }
        }
        return inputParamList;
    }

}
