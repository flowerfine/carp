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
package cn.sliew.carp.module.persistence;

import cn.sliew.carp.module.persistence.api.selectors.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

public class MybatisResourceVisitor<R> implements SelectorVisitor<LambdaQueryWrapper<R>, LambdaQueryWrapper<Object>> {

    private final LambdaQueryWrapper<R> queryWrapper;

    public MybatisResourceVisitor(LambdaQueryWrapper<R> queryWrapper) {
        this.queryWrapper = queryWrapper;
    }

    public void handleVisit(Selector selector) {
        if (selector instanceof AllSelector) {
            visit((AllSelector) selector);
        } else if (selector instanceof NotSelector) {
            visit((NotSelector) selector);
        } else if (selector instanceof AndSelector) {
            visit((AndSelector) selector);
        } else if (selector instanceof OrSelector) {
            visit((OrSelector) selector);
        }
    }

    @Override
    public LambdaQueryWrapper<R> visit(AllSelector selector) {
        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<R> visit(NotSelector selector) {
        queryWrapper.not(wrapper -> handleVisit(selector.getSelector()));
        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<R> visit(AndSelector selector) {
        queryWrapper.and(wrapper -> {
            selector.getSelectors().forEach(item -> handleVisit(item));
        });
        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<R> visit(OrSelector selector) {
        queryWrapper.or(wrapper -> {
            selector.getSelectors().forEach(item -> handleVisit(item));
        });
        return queryWrapper;
    }
}
