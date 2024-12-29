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

package cn.sliew.carp.module.alert.model;

import cn.sliew.carp.framework.common.dict.alert.CarpAlertStatus;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AlertList {

    private String version;
    private String receiver;
    // 外层状态不能作为内部列表的状态，要用内部列表的状态
    private CarpAlertStatus status;
    private List<Alert> alerts;
    private Map<String, String> groupLabels;
    private Map<String, String> commonLabels;
    private Map<String, String> commonAnnotations;
    private String externalURL;
    private String groupKey;
    private Long truncatedAlerts;
}
