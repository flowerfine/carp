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

import cn.hutool.core.convert.Convert;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowParam {

    private String name;
    private String alias;
    private Object value;
    private ParamDataType type;
    private Boolean isOverWrite;

    public WorkflowStepInputParam toWorkflowStepInputParam() {
        return WorkflowStepInputParam.builder()
            .name(name)
            .alias(alias)
            .value(value)
            .type(type)
            .isOverWrite(isOverWrite)
            .fromType(ParamFromType.CONSTANT)
            .from("")
            .build();
    }

    public Object value() throws Exception {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            switch (type) {
                case STRING:
                    return Convert.convert(String.class, value);
                    // jackson
//                    return JacksonUtil.getMapper().convertValue(value, String.class);
                    // fastjson
//                    return TypeUtils.castToJavaBean(value, String.class);
                case LONG:
                    return JacksonUtil.getMapper().convertValue(value, Long.class);
                case DOUBLE:
                    return JacksonUtil.getMapper().convertValue(value, Double.class);
                case BOOLEAN:
                    return JacksonUtil.getMapper().convertValue(value, Boolean.class);
                case OBJECT:
                    return JacksonUtil.getMapper().convertValue(value, ObjectNode.class);
                case ARRAY:
                    return JacksonUtil.getMapper().convertValue(value, ArrayNode.class);
                default:
                    return value;
            }
        } catch (Exception e) {
            throw new Exception("value can not match type", e);
        }
    }

    public void check() throws Exception {
        value();
    }

    public static void check(List<WorkflowParam> inputParamList) throws Exception {
        for (WorkflowParam inputParam : inputParamList) {
            inputParam.check();
        }
    }
}
