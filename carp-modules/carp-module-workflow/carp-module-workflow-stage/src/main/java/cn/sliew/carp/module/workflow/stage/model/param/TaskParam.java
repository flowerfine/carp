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
package cn.sliew.carp.module.workflow.stage.model.param;

import java.util.*;

public class TaskParam {

    private String name;
    private ParamDataType type;

    public static boolean isParamsChanged(List<TaskParam> list1, List<TaskParam> list2) {
        list1 = Objects.isNull(list1) ? new ArrayList<>() : list1;
        list2 = Objects.isNull(list2) ? new ArrayList<>() : list2;

        if (list1.size() != list2.size()) {
            return true;
        }
        Map<String, TaskParam> map2 = new HashMap<>(0);
        list2.forEach(x -> map2.put(x.name, x));
        for (TaskParam word1 : list1) {
            if (!map2.containsKey(word1.name)) {
                return true;
            }
            TaskParam word2 = map2.get(word1.name);
            if (word1.type != word2.type) {
                return true;
            }
        }
        return false;
    }

}
