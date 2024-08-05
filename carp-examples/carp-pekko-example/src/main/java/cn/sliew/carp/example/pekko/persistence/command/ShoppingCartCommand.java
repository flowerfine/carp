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

package cn.sliew.carp.example.pekko.persistence.command;

import cn.sliew.carp.example.pekko.persistence.state.ShoppingCartSummary;
import cn.sliew.carp.framework.pekko.serialization.CborSerializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.pattern.StatusReply;

/**
 * This interface defines all the commands that the ShoppingCart persistent actor supports.
 */
public interface ShoppingCartCommand extends CborSerializable {

    /**
     * A command to add an item to the cart.
     * <p>
     * It can reply with `StatusReply`, which is sent back to the caller when
     * all the events emitted by this command are successfully persisted.
     */
    class AddItem implements ShoppingCartCommand {
        public final String itemId;
        public final int quantity;
        public final ActorRef<StatusReply<ShoppingCartSummary>> replyTo;

        public AddItem(String itemId, int quantity, ActorRef<StatusReply<ShoppingCartSummary>> replyTo) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.replyTo = replyTo;
        }
    }

    /**
     * A command to remove an item from the cart.
     */
    class RemoveItem implements ShoppingCartCommand {
        public final String itemId;
        public final ActorRef<StatusReply<ShoppingCartSummary>> replyTo;

        @JsonCreator
        public RemoveItem(String itemId, ActorRef<StatusReply<ShoppingCartSummary>> replyTo) {
            this.itemId = itemId;
            this.replyTo = replyTo;
        }
    }

    /**
     * A command to adjust the quantity of an item in the cart.
     */
    class AdjustItemQuantity implements ShoppingCartCommand {
        public final String itemId;
        public final int quantity;
        public final ActorRef<StatusReply<ShoppingCartSummary>> replyTo;

        public AdjustItemQuantity(String itemId, int quantity, ActorRef<StatusReply<ShoppingCartSummary>> replyTo) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.replyTo = replyTo;
        }
    }

    /**
     * A command to checkout the shopping cart.
     * <p>
     * The reply type is the {@link StatusReply< ShoppingCartSummary >}, which will be returned when the events have been
     * emitted.
     */
    class Checkout implements ShoppingCartCommand {
        public final ActorRef<StatusReply<ShoppingCartSummary>> replyTo;

        @JsonCreator
        public Checkout(ActorRef<StatusReply<ShoppingCartSummary>> replyTo) {
            this.replyTo = replyTo;
        }
    }

    /**
     * A command to get the current state of the shopping cart.
     * <p>
     * The reply type is the {@link ShoppingCartSummary}
     */
    class Get implements ShoppingCartCommand {
        public final ActorRef<ShoppingCartSummary> replyTo;

        @JsonCreator
        public Get(ActorRef<ShoppingCartSummary> replyTo) {
            this.replyTo = replyTo;
        }
    }
}
