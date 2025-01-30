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
package cn.sliew.carp.module.scheduler.service;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobConfig;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigAddParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigPageParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigUpdateParam;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobConfigDTO;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigListParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

public interface ScheduleJobConfigService extends IService<ScheduleJobConfig> {

    PageResult<ScheduleJobConfigDTO> list(ScheduleJobConfigPageParam param);

    List<ScheduleJobConfigDTO> listAll(ScheduleJobConfigListParam param);

    ScheduleJobConfigDTO get(Long id);

    boolean add(ScheduleJobConfigAddParam param);

    boolean update(ScheduleJobConfigUpdateParam param);

    boolean delete(Long id);

    boolean deleteBatch(Collection<Long> ids);

}
