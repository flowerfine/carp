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

package cn.sliew.carp.module.system.service.impl;

import cn.sliew.carp.framework.common.dict.CarpEnumDictRegistry;
import cn.sliew.carp.framework.common.dict.DictDefinition;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.spring.util.PageUtil;
import cn.sliew.carp.module.system.service.SysDictDefinitionService;
import cn.sliew.carp.module.system.service.param.SysDictDefinitionParam;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SysDictDefinitionServiceImpl implements SysDictDefinitionService {

    @Override
    public PageResult<DictDefinition> listByPage(SysDictDefinitionParam param) {
        Collection<DictDefinition> dictTypes = selectAll();
        List<DictDefinition> filteredDictTypes = dictTypes.stream().filter(dictType -> {
            if (StringUtils.hasText(param.getCode())) {
                return dictType.getCode().contains(param.getCode());
            }
            return true;
        }).filter(dictType -> {
            if (StringUtils.hasText(param.getName())) {
                return dictType.getName().contains(param.getName());
            }
            return true;
        }).collect(Collectors.toList());

        return PageUtil.buildPage(param, filteredDictTypes);
    }

    @Override
    public Optional<DictDefinition> getByCode(String code) {
        return CarpEnumDictRegistry.INSTANCE.getDictDefinition(code);
    }

    @Override
    public Collection<DictDefinition> selectAll() {
        return CarpEnumDictRegistry.INSTANCE.getAllDefinitions();
    }
}
