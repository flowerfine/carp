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

package cn.sliew.carp.framework.common.dict;

import cn.hutool.core.util.ArrayUtil;
import cn.sliew.carp.framework.common.dict.common.IsDeleted;
import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.dict.security.*;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Collection;

public enum EnumDictRegistry implements DictRegistry {
    INSTANCE;

    private static final Table<DictDefinition, String, DictInstance> DICT_TABLE = HashBasedTable.create();

    static {
        register(DictType.YES_OR_NO, YesOrNo.values());
        register(DictType.IS_DELETED, IsDeleted.values());

        register(DictType.SEC_APPLICATION_TYPE, SecApplicationType.values());
        register(DictType.SEC_APPLICATION_STATUS, SecApplicationStatus.values());
        register(DictType.USER_TYPE, UserType.values());
        register(DictType.USER_STATUS, UserStatus.values());
        register(DictType.ROLE_TYPE, RoleType.values());
        register(DictType.ROLE_STATUS, RoleStatus.values());
        register(DictType.RESOURCE_WEB_TYPE, ResourceWebType.values());
        register(DictType.RESOURCE_DATA_TYPE, ResourceDataType.values());
        register(DictType.RESOURCE_STATUS, ResourceStatus.values());
    }

    public static void register(DictDefinition definition, DictInstance... instances) {
        if (ArrayUtil.isNotEmpty(instances)) {
            for (DictInstance instance : instances) {
                DICT_TABLE.put(definition, instance.getValue(), instance);
            }
        }
    }

    @Override
    public Collection<DictDefinition> getAllDefinitions() {
        return DICT_TABLE.rowKeySet();
    }

    @Override
    public Collection<DictInstance> getDictInstance(DictDefinition definition) {
        return DICT_TABLE.row(definition).values();
    }

    @Override
    public boolean exists(DictDefinition definition, String instanceName) {
        return DICT_TABLE.contains(DICT_TABLE, instanceName);
    }

    @Override
    public DictInstance getDictInstance(DictDefinition definition, String instanceName) {
        return DICT_TABLE.get(definition, instanceName);
    }
}
