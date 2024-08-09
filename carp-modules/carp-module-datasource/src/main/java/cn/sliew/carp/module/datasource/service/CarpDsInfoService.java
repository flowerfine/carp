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

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.repository.entity.DsInfo;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.carp.module.datasource.service.param.DsInfoListParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

public interface CarpDsInfoService extends IService<DsInfo> {

    PageResult<DsInfoDTO> list(DsInfoListParam param);

    List<DsInfoDTO> listByType(DataSourceType type);

    DsInfoDTO selectOne(Long id, boolean decrypt);

    boolean add(DataSourceInfo dataSource);

    boolean update(Long id, DataSourceInfo dataSource);

    boolean deleteById(Long id);

    boolean deleteBatch(Collection<Long> ids);
}
