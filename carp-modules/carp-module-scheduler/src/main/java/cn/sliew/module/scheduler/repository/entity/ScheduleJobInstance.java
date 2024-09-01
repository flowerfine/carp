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

package cn.sliew.module.scheduler.repository.entity;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("schedule_job_instance")
@Schema(name = "ScheduleJobInstance", description = "schedule job instance")
public class ScheduleJobInstance extends BaseAuditDO {

    @Schema(description = "任务配置id")
    @TableField("job_config_id")
    private Long jobConfigId;

    @Schema(description = "实例名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "CRON表达式")
    @TableField("cron")
    private String cron;

    @Schema(description = "参数")
    @TableField("params")
    private String params;

    @Schema(description = "超时时间（毫秒）")
    @TableField("timeout")
    private Long timeout;

    @Schema(description = "状态")
    @TableField("`status`")
    private String status;

    @Schema(description = "remark")
    @TableField("remark")
    private String remark;
}
