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

package cn.sliew.carp.example.pekko.persistence.event;

import cn.sliew.carp.framework.pekko.serialization.CborSerializable;

import java.time.Instant;

public interface ShoppingCartEvent extends CborSerializable {

    final class ItemAdded implements ShoppingCartEvent {
        public final String cartId;
        public final String itemId;
        public final int quantity;

        public ItemAdded(String cartId, String itemId, int quantity) {
            this.cartId = cartId;
            this.itemId = itemId;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "ItemAdded(" + cartId + "," + itemId + "," + quantity + ")";
        }
    }

    final class ItemRemoved implements ShoppingCartEvent {
        public final String cartId;
        public final String itemId;

        public ItemRemoved(String cartId, String itemId) {
            this.cartId = cartId;
            this.itemId = itemId;
        }

        @Override
        public String toString() {
            return "ItemRemoved(" + cartId + "," + itemId + ")";
        }
    }

    final class ItemQuantityAdjusted implements ShoppingCartEvent {
        public final String cartId;
        public final String itemId;
        public final int quantity;

        public ItemQuantityAdjusted(String cartId, String itemId, int quantity) {
            this.cartId = cartId;
            this.itemId = itemId;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "ItemQuantityAdjusted(" + cartId + "," + itemId + "," + quantity + ")";
        }
    }

    class CheckedOut implements ShoppingCartEvent {

        public final String cartId;
        public final Instant eventTime;

        public CheckedOut(String cartId, Instant eventTime) {
            this.cartId = cartId;
            this.eventTime = eventTime;
        }

        @Override
        public String toString() {
            return "CheckedOut(" + cartId + "," + eventTime + ")";
        }
    }
}
