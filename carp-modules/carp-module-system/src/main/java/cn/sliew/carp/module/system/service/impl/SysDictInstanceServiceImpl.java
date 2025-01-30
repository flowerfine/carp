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
import cn.sliew.carp.framework.common.dict.DictInstance;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.system.service.SysDictDefinitionService;
import cn.sliew.carp.module.system.service.SysDictInstanceService;
import cn.sliew.carp.module.system.service.param.SysDictInstanceParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SysDictInstanceServiceImpl implements SysDictInstanceService {

    @Autowired
    private SysDictDefinitionService sysDictDefinitionService;

    @Override
    public Collection<DictInstance> selectByDefinition(String code) {
        Optional<DictDefinition> optional = sysDictDefinitionService.getByCode(code);
        if (optional.isPresent()) {
            return CarpEnumDictRegistry.INSTANCE.getDictInstance(optional.get());
        }
        return Collections.emptyList();
    }

    @Override
    public PageResult<DictInstance> listByPage(SysDictInstanceParam param) {
        Collection<DictInstance> dictInstances;
        if (param.getDictDefinitionCode() != null) {
            dictInstances = selectByDefinition(param.getDictDefinitionCode());
        } else {
            dictInstances = selectAll();
        }
        List<DictInstance> filteredDictInstances = dictInstances.stream().filter(dictInstance -> {
            if (StringUtils.hasText(param.getValue())) {
                return dictInstance.getValue().contains(param.getValue());
            }
            return true;
        }).filter(dictInstance -> {
            if (StringUtils.hasText(param.getLabel())) {
                return dictInstance.getLabel().contains(param.getLabel());
            }
            return true;
        }).collect(Collectors.toList());

        PageResult<DictInstance> result = new PageResult<>(param.getCurrent(), param.getPageSize(), Long.valueOf(filteredDictInstances.size()));
        Long from = (param.getCurrent() - 1) * param.getPageSize();
        Long to = from + param.getPageSize();
        if (from >= filteredDictInstances.size()) {
            result.setRecords(Collections.emptyList());
            return result;
        }

        result.setRecords(filteredDictInstances.subList(from.intValue(), to.intValue() < filteredDictInstances.size() ? to.intValue() : filteredDictInstances.size() - 1));
        return result;
    }

    @Override
    public Collection<DictInstance> selectAll() {
        return CarpEnumDictRegistry.INSTANCE.getAllInstances();
    }
}
