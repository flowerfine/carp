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

import java.util.function.Predicate;

public class BaseResourceVisitor<R> implements SelectorVisitor<Predicate, Predicate<Object>> {

    @Override
    public Predicate visit(AllSelector selector) {
        return r -> true;
    }

    @Override
    public Predicate visit(NotSelector selector) {
        Predicate<R> predicate = selector.getSelector().accept(this);
        return predicate.negate();
    }

    @Override
    public Predicate visit(AndSelector selector) {
        return selector.getSelectors().stream().map((item) -> item.accept(this))
                .reduce(Predicate::and).orElseThrow(() -> new IllegalStateException("AndSelector is empty"));
    }

    @Override
    public Predicate visit(OrSelector selector) {
        return selector.getSelectors().stream().map((item) -> item.accept(this))
                .reduce(Predicate::or).orElseThrow(() -> new IllegalStateException("AndSelector is empty"));
    }
}
