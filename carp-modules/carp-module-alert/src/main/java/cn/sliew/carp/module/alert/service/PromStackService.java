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
package cn.sliew.carp.module.alert.service;

import cn.sliew.carp.framework.common.model.BasePageParam;
import cn.sliew.carp.module.alert.service.dto.CarpAlertAlertmanagerDTO;
import cn.sliew.carp.module.alert.service.dto.CarpAlertPrometheusDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

public interface PromStackService {

    Page<CarpAlertPrometheusDTO> pagePrometheus(BasePageParam param);

    CarpAlertPrometheusDTO getPrometheus(Long id);

    boolean addPrometheus(CarpAlertPrometheusDTO param);

    boolean updatePrometheus(CarpAlertPrometheusDTO param);

    boolean deletePrometheus(Long id);

    boolean deletePrometheusBatch(Collection<Long> ids);

    Page<CarpAlertAlertmanagerDTO> pageAlertManager(BasePageParam param);

    CarpAlertAlertmanagerDTO getAlertManager(Long id);

    boolean addAlertManager(CarpAlertAlertmanagerDTO param);

    boolean updateAlertManager(CarpAlertAlertmanagerDTO param);

    boolean deleteAlertManager(Long id);

    boolean deleteAlertManagerBatch(Collection<Long> ids);
}
