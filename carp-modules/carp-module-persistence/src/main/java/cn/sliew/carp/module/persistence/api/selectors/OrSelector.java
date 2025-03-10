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
package cn.sliew.carp.module.persistence.api.selectors;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public final class OrSelector implements Selector {

    private final List<Selector> selectors;

    OrSelector(Selector... selectors) {
        checkArgument(ArrayUtils.isNotEmpty(selectors), "No selectors provided");
        this.selectors = Arrays.asList(selectors);
    }

    public <C> C accept(SelectorVisitor<C, ?> visitor) {
        return visitor.visit(this);
    }
}
