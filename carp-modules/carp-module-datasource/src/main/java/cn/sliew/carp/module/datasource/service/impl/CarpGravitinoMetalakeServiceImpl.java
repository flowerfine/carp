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
package cn.sliew.carp.module.datasource.service.impl;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.datasource.service.CarpGravitinoMetalakeService;
import cn.sliew.carp.module.datasource.service.convert.GravitinoMetalakeConvert;
import cn.sliew.carp.module.datasource.service.dto.GravitinoMetalakeDTO;
import cn.sliew.carp.module.datasource.util.PageUtil;
import org.apache.gravitino.client.GravitinoAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CarpGravitinoMetalakeServiceImpl implements CarpGravitinoMetalakeService {

    @Autowired
    private GravitinoAdminClient adminClient;

    @Override
    public PageResult<GravitinoMetalakeDTO> page(PageParam param) {
        List<GravitinoMetalakeDTO> list = GravitinoMetalakeConvert.INSTANCE.toDto(Arrays.asList(adminClient.listMetalakes()));
        return PageUtil.buildPage(param, list);
    }
}
