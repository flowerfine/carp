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
package cn.sliew.carp.module.cep.service.impl;

import cn.sliew.carp.module.cep.repository.entity.CarpCepFunctionCategory;
import cn.sliew.carp.module.cep.repository.mapper.CarpCepFunctionCategoryMapper;
import cn.sliew.carp.module.cep.service.CarpCepFunctionCategoryService;
import cn.sliew.carp.module.cep.service.convert.CarpCepFunctionCategoryConvert;
import cn.sliew.carp.module.cep.service.dto.CarpCepFunctionCategoryDTO;
import cn.sliew.carp.module.cep.service.param.CepFunctionCategoryListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CarpCepFunctionCategoryServiceImpl extends ServiceImpl<CarpCepFunctionCategoryMapper, CarpCepFunctionCategory> implements CarpCepFunctionCategoryService {

    @Override
    public List<CarpCepFunctionCategoryDTO> listAll(CepFunctionCategoryListParam param) {
        LambdaQueryWrapper<CarpCepFunctionCategory> queryWrapper = Wrappers.lambdaQuery(CarpCepFunctionCategory.class)
            .eq(CarpCepFunctionCategory::getNamespace, param.getNamespace())
            .orderByAsc(CarpCepFunctionCategory::getOrder);
        List<CarpCepFunctionCategory> categories = baseMapper.selectList(queryWrapper);
        return CarpCepFunctionCategoryConvert.INSTANCE.toDto(categories);
    }
}
