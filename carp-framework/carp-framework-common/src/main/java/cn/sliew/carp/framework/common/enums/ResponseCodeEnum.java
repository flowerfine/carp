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

package cn.sliew.carp.framework.common.enums;

import lombok.Getter;

/**
 * @author gleiyu
 */
@Getter
public enum ResponseCodeEnum {
    /**
     * 操作结果枚举类
     * 530 自定义异常
     */
    SUCCESS("204", "response.success"),
    ERROR_NO_PRIVILEGE("403", "response.error.no.privilege"),
    ERROR_UNAUTHORIZED("401", "response.error.unauthorized"),
    ERROR("500", "response.error"),
    ERROR_CUSTOM("530", ""),
    ERROR_EMAIL("531", "response.error.email"),
    ERROR_DUPLICATE_DATA("532", "response.error.duplicate.data"),
    ERROR_UNSUPPORTED_CONNECTION("533", "response.error.unsupported.connection"),
    ERROR_CONNECTION("534", "response.error.connection");

    private String code;
    private String value;

    ResponseCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
