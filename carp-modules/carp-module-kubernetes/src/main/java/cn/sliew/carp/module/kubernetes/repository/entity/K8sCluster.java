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

package cn.sliew.carp.module.kubernetes.repository.entity;

import cn.sliew.carp.framework.common.dict.k8s.ClusterStatus;
import cn.sliew.carp.framework.common.dict.k8s.ClusterType;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import cn.sliew.carp.framework.mybatis.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("carp_k8s_cluster")
@Schema(name = "K8sCluster", description = "k8s 集群")
public class K8sCluster extends BaseAuditDO {

    @Schema(description = "type")
    @TableField("type")
    private ClusterType type;

    @Schema(description = "uuid")
    @TableField("uuid")
    private String uuid;

    @Schema(description = "名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "spec")
    @TableField("spec")
    private String spec;

    @Schema(description = "status")
    @TableField("`status`")
    private String status;

    @Schema(description = "cluster status. 0: disabled, 1: enabled")
    @TableField("cluster_status")
    private ClusterStatus clusterStatus;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
