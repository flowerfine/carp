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

package cn.sliew.carp.module.kubernetes.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.kubernetes.service.K8sClusterService;
import cn.sliew.carp.module.kubernetes.service.entity.Cluster;
import cn.sliew.carp.module.kubernetes.service.param.K8sClusterAddParam;
import cn.sliew.carp.module.kubernetes.service.param.K8sClusterPageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/kubernetes/cluster")
@Tag(name = "Kubernetes模块-集群管理")
public class K8sClusterController {

    @Autowired
    private K8sClusterService k8sClusterService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<Cluster> list(@Valid K8sClusterPageParam param) {
        return k8sClusterService.list(param);
    }

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<Cluster> listAll() {
        return k8sClusterService.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public Cluster get(@PathVariable("id") Long id) {
        return k8sClusterService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody K8sClusterAddParam param) {
        return k8sClusterService.add(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return k8sClusterService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return k8sClusterService.deleteBatch(ids);
    }
}
