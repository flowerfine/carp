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

package cn.sliew.carp.framework.common.constant;

import java.io.File;

public enum Constants {
    ;

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MS_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String SEPARATOR = "-";
    public static final String PATH_SEPARATOR = File.separator;

    public static final String DEFAULT_TIMEZONE = "GMT+8";
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String CODEC_STR_PREFIX = "Encrypted:";
}
