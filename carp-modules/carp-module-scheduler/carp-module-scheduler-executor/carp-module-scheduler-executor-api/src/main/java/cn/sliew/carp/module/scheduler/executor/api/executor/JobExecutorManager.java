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
package cn.sliew.carp.module.scheduler.executor.api.executor;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleEngineType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.module.scheduler.executor.api.dict.CarpScheduleExecuteType;

import java.util.List;
import java.util.Set;

public interface JobExecutorManager {

    List<CarpScheduleEngineType> listEngines();

    Set<CarpScheduleJobType> listTypes(CarpScheduleEngineType engineType);

    List<CarpScheduleExecuteType> listExecutorTypes(CarpScheduleEngineType engineType, CarpScheduleJobType jobType);

    JobExecutor getExecutor(CarpScheduleEngineType engineType, CarpScheduleJobType jobType);
}
