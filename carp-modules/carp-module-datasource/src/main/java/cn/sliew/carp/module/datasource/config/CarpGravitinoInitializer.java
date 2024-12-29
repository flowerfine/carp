/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *nch
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.carp.module.datasource.config;

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;
import cn.sliew.carp.module.datasource.service.CarpDsInfoService;
import cn.sliew.carp.module.datasource.service.CarpGravitinoMetalakeService;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarpGravitinoInitializer implements InitializingBean {

    @Autowired
    private CarpGravitinoProperties properties;
    @Autowired
    private CarpDsInfoService carpDsInfoService;
    @Autowired
    private CarpGravitinoMetalakeService gravitinoMetalakeService;

    @Override
    public void afterPropertiesSet() throws Exception {
        initialize();
    }

    private void initialize() {
        // 初始化 metalake
        initMetalake(properties.getMetalake());
        // 初始化 catalog
        initDataSource(properties.getMetalake(), CarpDataSourceType.MYSQL);
        initDataSource(properties.getMetalake(), CarpDataSourceType.POSTGRESQL);
        initDataSource(properties.getMetalake(), CarpDataSourceType.HIVE);
        initDataSource(properties.getMetalake(), CarpDataSourceType.ICEBERG);
        initDataSource(properties.getMetalake(), CarpDataSourceType.DORIS);
        initDataSource(properties.getMetalake(), CarpDataSourceType.KAFKA);
        initDataSource(properties.getMetalake(), CarpDataSourceType.HDFS);
    }

    private void initMetalake(String metalakeName) {
        gravitinoMetalakeService.tryAddMetalake(metalakeName);
    }

    private void initDataSource(String metalakeName, CarpDataSourceType type) {
        List<DsInfoDTO> dsInfoDTOS = carpDsInfoService.listByType(type);
        for (DsInfoDTO dsInfoDTO : dsInfoDTOS) {
            gravitinoMetalakeService.tryAddCatalog(metalakeName, dsInfoDTO);
        }
    }
}
