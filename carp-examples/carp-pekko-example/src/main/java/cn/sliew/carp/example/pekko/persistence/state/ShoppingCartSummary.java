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

package cn.sliew.carp.example.pekko.persistence.state;

import cn.sliew.carp.framework.pekko.serialization.CborSerializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Summary of the shopping cart state, used in reply messages.
 */
public final class ShoppingCartSummary implements CborSerializable {

    public final Map<String, Integer> items;
    public final boolean checkedOut;

    public ShoppingCartSummary(Map<String, Integer> items, boolean checkedOut) {
        // Summary is included in messages and should therefore be immutable
        this.items = Collections.unmodifiableMap(new HashMap<>(items));
        this.checkedOut = checkedOut;
    }
}