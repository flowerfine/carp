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

import cn.sliew.carp.framework.common.dict.DictDefinition;
import cn.sliew.carp.framework.common.dict.DictInstance;
import cn.sliew.carp.framework.common.dict.EnumDictRegistry;
import cn.sliew.carp.module.system.service.SysDictService;
import cn.sliew.carp.module.system.service.SysDictTypeService;
import cn.sliew.carp.module.system.service.param.SysDictParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Override
    public Collection<DictInstance> selectByType(String code) {
        Optional<DictDefinition> optional = EnumDictRegistry.INSTANCE.getDictDefinition(code);
        if (optional.isPresent()) {
            return EnumDictRegistry.INSTANCE.getDictInstance(optional.get());
        }
        return Collections.emptyList();
    }

    @Override
    public Page<DictInstance> listByPage(SysDictParam param) {
        Collection<DictInstance> dictInstances;
        if (param.getDictType() != null) {
            dictInstances = selectByType(param.getDictType());
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

        Page<DictInstance> result = new Page<>(param.getCurrent(), param.getPageSize(), filteredDictInstances.size());
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
        return EnumDictRegistry.INSTANCE.getAllInstances();
    }
}
