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

package cn.sliew.carp.module.datasource.service.impl;

import cn.sliew.carp.module.datasource.repository.entity.DsCategory;
import cn.sliew.carp.module.datasource.repository.entity.DsType;
import cn.sliew.carp.module.datasource.repository.mapper.DsCategoryMapper;
import cn.sliew.carp.module.datasource.repository.mapper.DsTypeMapper;
import cn.sliew.carp.module.datasource.service.CarpDsCategoryService;
import cn.sliew.carp.module.datasource.service.convert.DsCategoryConvert;
import cn.sliew.carp.module.datasource.service.convert.DsTypeConvert;
import cn.sliew.carp.module.datasource.service.dto.DsCategoryDTO;
import cn.sliew.carp.module.datasource.service.dto.DsTypeDTO;
import cn.sliew.carp.module.datasource.service.param.DsTypeListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpDsCategoryServiceImpl extends ServiceImpl<DsCategoryMapper, DsCategory> implements CarpDsCategoryService {

    @Autowired
    private DsTypeMapper dsTypeMapper;

    public List<DsCategoryDTO> listAll() {
        LambdaQueryWrapper<DsCategory> queryWrapper = Wrappers.lambdaQuery(DsCategory.class)
                .orderByAsc(DsCategory::getOrder);
        List<DsCategory> categories = baseMapper.selectList(queryWrapper);
        return DsCategoryConvert.INSTANCE.toDto(categories);
    }

    @Override
    public List<DsTypeDTO> listTypes(DsTypeListParam param) {
        List<DsType> dsTypes = dsTypeMapper.listTypes(param.getCategoryId(), param.getType());
        return DsTypeConvert.INSTANCE.toDto(dsTypes);
    }
}
