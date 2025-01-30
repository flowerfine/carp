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
package cn.sliew.carp.module.datasource.service;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoCatalogDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoMetalakeDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoSchemaDTO;

import java.util.List;

public interface CarpGravitinoMetalakeService {

    PageResult<GravitinoMetalakeDTO> page(PageParam param);

    List<GravitinoCatalogDTO> listCatalogs(String metalakeName);

    List<GravitinoSchemaDTO> listSchema(String metalakeName, String catalogName);

    void tryAddMetalake(String metalakeName);

    void tryAddCatalog(String metalakeName, DsInfoDTO dto);

    void tryUpdateCatalog(String metalakeName, DsInfoDTO dto);

    void tryDeleteCatalog(String metalakeName, DsInfoDTO dto);
}
