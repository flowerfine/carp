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

import cn.sliew.carp.example.pekko.persistence.ShoppingCart;
import cn.sliew.carp.framework.pekko.serialization.CborSerializable;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The state for the {@link ShoppingCart} entity.
 */
public final class ShoppingCartState implements CborSerializable {

    private Map<String, Integer> items = new HashMap<>();
    private Optional<Instant> checkoutDate = Optional.empty();

    public boolean isCheckedOut() {
        return checkoutDate.isPresent();
    }

    public Optional<Instant> getCheckoutDate() {
        return checkoutDate;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean hasItem(String itemId) {
        return items.containsKey(itemId);
    }

    public ShoppingCartState updateItem(String itemId, int quantity) {
        if (quantity == 0) {
            items.remove(itemId);
        } else {
            items.put(itemId, quantity);
        }
        return this;
    }

    public ShoppingCartState removeItem(String itemId) {
        items.remove(itemId);
        return this;
    }

    public ShoppingCartState checkout(Instant now) {
        checkoutDate = Optional.of(now);
        return this;
    }

    public ShoppingCartSummary toSummary() {
        return new ShoppingCartSummary(items, isCheckedOut());
    }

}