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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.cep.repository.entity.CarpCepFunction;
import cn.sliew.carp.module.cep.repository.mapper.CarpCepFunctionMapper;
import cn.sliew.carp.module.cep.service.CarpCepFunctionService;
import cn.sliew.carp.module.cep.service.convert.CarpCepFunctionConvert;
import cn.sliew.carp.module.cep.service.dto.CarpCepFunctionDTO;
import cn.sliew.carp.module.cep.service.param.CepFunctionPageParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarpCepFunctionServiceImpl extends ServiceImpl<CarpCepFunctionMapper, CarpCepFunction> implements CarpCepFunctionService {

    @Override
    public PageResult<CarpCepFunctionDTO> page(CepFunctionPageParam param) {
        Page<CarpCepFunction> page = PageUtil.buildPageParam(param);
        Page<CarpCepFunction> carpCepFunctionPage = baseMapper.page(page, param.getNamespace(), param.getCategoryId());
        return PageUtil.buildPageResult(carpCepFunctionPage, CarpCepFunctionConvert.INSTANCE::toDto);
    }
}
