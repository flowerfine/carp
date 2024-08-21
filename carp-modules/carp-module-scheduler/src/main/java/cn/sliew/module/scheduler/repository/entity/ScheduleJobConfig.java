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
@TableName("schedule_job_config")
@Schema(name = "ScheduleJobConfig", description = "schedule job config")
public class ScheduleJobConfig extends BaseAuditDO {

    @Schema(description = "任务分组 id")
    @TableField("job_group_id")
    private Long jobGroupId;

    @Schema(description = "任务类型")
    @TableField("`type`")
    private String type;

    @Schema(description = "任务名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "任务处理器")
    @TableField("`handler`")
    private String handler;

    @Schema(description = "remark")
    @TableField("remark")
    private String remark;
}
