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

package cn.sliew.carp.module.scheduler.repository.entity;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleStatus;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("carp_schedule_job_instance")
public class ScheduleJobInstance extends BaseAuditDO {

    @TableField("job_config_id")
    private Long jobConfigId;

    @TableField("`name`")
    private String name;

    @TableField("cron")
    private String cron;

    @TableField("timezone")
    private String timezone;

    @TableField("start_time")
    private Date startTime;

    @TableField("end_time")
    private Date endTime;

    @TableField("props")
    private String props;

    @TableField("params")
    private String params;

    @TableField("timeout")
    private Long timeout;

    @TableField("`status`")
    private CarpScheduleStatus status;

    @TableField("remark")
    private String remark;
}
