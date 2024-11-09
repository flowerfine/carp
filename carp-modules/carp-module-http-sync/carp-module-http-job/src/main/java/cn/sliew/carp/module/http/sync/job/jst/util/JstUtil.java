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

package cn.sliew.carp.module.http.sync.job.jst.util;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstResult;
import org.springframework.util.CollectionUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

public enum JstUtil {
    ;

    public static <S, T> JstResultWrapper<T> convertResult(JstResult<S> jstResult, Function<S, T> convert) {
        JstResultWrapper jstResultWrapper = BeanUtil.copyProperties(jstResult, JstResultWrapper.class);
        if (CollectionUtils.isEmpty(jstResult.getDatas()) == false) {
            jstResultWrapper.setDatas(jstResult.getDatas().stream().map(convert).collect(Collectors.toList()));
        }
        return jstResultWrapper;
    }
}
