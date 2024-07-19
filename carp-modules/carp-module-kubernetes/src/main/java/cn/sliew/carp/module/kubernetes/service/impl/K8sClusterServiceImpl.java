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

package cn.sliew.carp.module.kubernetes.service.impl;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.kubernetes.repository.entity.K8sCluster;
import cn.sliew.carp.module.kubernetes.repository.mapper.K8sClusterMapper;
import cn.sliew.carp.module.kubernetes.service.K8sClusterService;
import cn.sliew.carp.module.kubernetes.service.convert.K8sClusterConvert;
import cn.sliew.carp.module.kubernetes.service.entity.Cluster;
import cn.sliew.carp.module.kubernetes.service.param.K8sClusterAddParam;
import cn.sliew.carp.module.kubernetes.service.param.K8sClusterPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class K8sClusterServiceImpl extends ServiceImpl<K8sClusterMapper, K8sCluster> implements K8sClusterService {

    @Override
    public PageResult<Cluster> list(K8sClusterPageParam param) {
        Page<K8sCluster> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<K8sCluster> queryChainWrapper = Wrappers.lambdaQuery(K8sCluster.class)
                .like(StringUtils.hasText(param.getName()), K8sCluster::getName, param.getName())
                .orderByAsc(K8sCluster::getId);
        Page<K8sCluster> secRolePage = page(page, queryChainWrapper);
        PageResult<Cluster> pageResult = new PageResult<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        pageResult.setRecords(K8sClusterConvert.INSTANCE.toDto(secRolePage.getRecords()));
        return pageResult;
    }

    @Override
    public List<Cluster> listAll() {
        return null;
    }

    @Override
    public Cluster get(Long id) {
        return null;
    }

    @Override
    public boolean add(K8sClusterAddParam param) {
        return false;
    }
}
