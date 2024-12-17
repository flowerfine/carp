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

package cn.sliew.carp.module.dataservice.service.impl;

import cn.sliew.carp.module.dataservice.domain.DataServiceExecutor;
import cn.sliew.carp.module.dataservice.domain.DataServiceExecutorRegistry;
import cn.sliew.carp.module.dataservice.service.CarpDataServiceConfigService;
import cn.sliew.carp.module.dataservice.service.CarpDataServiceExecutorService;
import cn.sliew.carp.module.dataservice.service.dto.CarpDataServiceConfigDTO;
import cn.sliew.carp.module.dataservice.service.param.ExecuteParam;
import cn.sliew.carp.module.datasource.service.CarpDsInfoService;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarpDataServiceExecutorServiceImpl implements CarpDataServiceExecutorService {

    @Autowired
    private DataServiceExecutorRegistry registry;
    @Autowired
    private CarpDataServiceConfigService dataServiceConfigService;
    @Autowired
    private CarpDsInfoService carpDsInfoService;

    @Override
    public void deploy(Long configId) {
        CarpDataServiceConfigDTO carpDataServiceConfigDTO = dataServiceConfigService.get(configId);
        DsInfoDTO dsInfoDTO = carpDsInfoService.selectOne(carpDataServiceConfigDTO.getDsId(), true);
        DataServiceExecutor dataServiceExecutor = registry.get(dsInfoDTO.getDsType().getType());
        dataServiceExecutor.register(configId.toString(), carpDataServiceConfigDTO.getQueryScript(), dsInfoDTO);
    }

    @Override
    public void stop(Long configId) {
        CarpDataServiceConfigDTO carpDataServiceConfigDTO = dataServiceConfigService.get(configId);
        DsInfoDTO dsInfoDTO = carpDsInfoService.selectOne(carpDataServiceConfigDTO.getDsId(), true);
        DataServiceExecutor dataServiceExecutor = registry.get(dsInfoDTO.getDsType().getType());
        dataServiceExecutor.unregister(configId.toString(), dsInfoDTO);
    }

    @Override
    public Object execute(ExecuteParam param) {
        CarpDataServiceConfigDTO carpDataServiceConfigDTO = dataServiceConfigService.get(param.getId());
        DsInfoDTO dsInfoDTO = carpDsInfoService.selectOne(carpDataServiceConfigDTO.getDsId(), true);
        DataServiceExecutor dataServiceExecutor = registry.get(dsInfoDTO.getDsType().getType());
        return dataServiceExecutor.execute(param.getId().toString(), carpDataServiceConfigDTO.getQueryScript(), param.getParams(), dsInfoDTO);
    }
}
