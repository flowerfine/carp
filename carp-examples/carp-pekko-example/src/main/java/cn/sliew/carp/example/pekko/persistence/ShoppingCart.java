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

package cn.sliew.carp.example.pekko.persistence;

import cn.sliew.carp.example.pekko.persistence.command.ShoppingCartCommand;
import cn.sliew.carp.example.pekko.persistence.event.ShoppingCartEvent;
import cn.sliew.carp.example.pekko.persistence.state.ShoppingCartState;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.SupervisorStrategy;
import org.apache.pekko.pattern.StatusReply;
import org.apache.pekko.persistence.typed.PersistenceId;
import org.apache.pekko.persistence.typed.javadsl.*;

import java.time.Duration;
import java.time.Instant;

/**
 * This is an event sourced actor. It has a state, {@link ShoppingCartState}, which
 * stores the current shopping cart items and whether it's checked out.
 * <p>
 * Event sourced actors are interacted with by sending them commands,
 * see classes implementing {@link ShoppingCartCommand}.
 * <p>
 * Commands get translated to events, see classes implementing {@link ShoppingCartEvent}.
 * It's the events that get persisted by the entity. Each event will have an event handler
 * registered for it, and an event handler updates the current state based on the event.
 * This will be done when the event is first created, and it will also be done when the entity is
 * loaded from the database - each event will be replayed to recreate the state
 * of the entity.
 */
public class ShoppingCart
        extends EventSourcedBehavior<ShoppingCartCommand, ShoppingCartEvent, ShoppingCartState> {

    public static Behavior<ShoppingCartCommand> create(String cartId) {
        return new ShoppingCart(cartId);
    }

    private final String cartId;

    private ShoppingCart(String cartId) {
        super(PersistenceId.of("ShoppingCart", cartId),
                SupervisorStrategy.restartWithBackoff(Duration.ofMillis(200), Duration.ofSeconds(5), 0.1));
        this.cartId = cartId;
    }

    @Override
    public ShoppingCartState emptyState() {
        return new ShoppingCartState();
    }

    private final CheckedOutCommandHandlers checkedOutCommandHandlers = new CheckedOutCommandHandlers();
    private final OpenShoppingCartCommandHandlers openShoppingCartCommandHandlers = new OpenShoppingCartCommandHandlers();

    @Override
    public CommandHandler<ShoppingCartCommand, ShoppingCartEvent, ShoppingCartState> commandHandler() {
        CommandHandlerBuilder<ShoppingCartCommand, ShoppingCartEvent, ShoppingCartState> b =
                newCommandHandlerBuilder();

        b.forState(state -> !state.isCheckedOut())
                .onCommand(ShoppingCartCommand.AddItem.class, openShoppingCartCommandHandlers::onAddItem)
                .onCommand(ShoppingCartCommand.RemoveItem.class, openShoppingCartCommandHandlers::onRemoveItem)
                .onCommand(ShoppingCartCommand.AdjustItemQuantity.class, openShoppingCartCommandHandlers::onAdjustItemQuantity)
                .onCommand(ShoppingCartCommand.Checkout.class, openShoppingCartCommandHandlers::onCheckout);

        b.forState(state -> state.isCheckedOut())
                .onCommand(ShoppingCartCommand.AddItem.class, checkedOutCommandHandlers::onAddItem)
                .onCommand(ShoppingCartCommand.RemoveItem.class, checkedOutCommandHandlers::onRemoveItem)
                .onCommand(ShoppingCartCommand.AdjustItemQuantity.class, checkedOutCommandHandlers::onAdjustItemQuantity)
                .onCommand(ShoppingCartCommand.Checkout.class, checkedOutCommandHandlers::onCheckout);

        b.forAnyState()
                .onCommand(ShoppingCartCommand.Get.class, this::onGet);

        return b.build();
    }

    private Effect<ShoppingCartEvent, ShoppingCartState> onGet(ShoppingCartState state, ShoppingCartCommand.Get cmd) {
        cmd.replyTo.tell(state.toSummary());
        return Effect().none();
    }

    private class OpenShoppingCartCommandHandlers {

        Effect<ShoppingCartEvent, ShoppingCartState> onAddItem(ShoppingCartState state, ShoppingCartCommand.AddItem cmd) {
            if (state.hasItem(cmd.itemId)) {
                cmd.replyTo.tell(StatusReply.error(
                        "Item '" + cmd.itemId + "' was already added to this shopping cart"));
                return Effect().none();
            } else if (cmd.quantity <= 0) {
                cmd.replyTo.tell(StatusReply.error("Quantity must be greater than zero"));
                return Effect().none();
            } else {
                return Effect().persist(new ShoppingCartEvent.ItemAdded(cartId, cmd.itemId, cmd.quantity))
                        .thenRun(updatedCart -> cmd.replyTo.tell(StatusReply.success(updatedCart.toSummary())));
            }
        }

        Effect<ShoppingCartEvent, ShoppingCartState> onRemoveItem(ShoppingCartState state, ShoppingCartCommand.RemoveItem cmd) {
            if (state.hasItem(cmd.itemId)) {
                return Effect().persist(new ShoppingCartEvent.ItemRemoved(cartId, cmd.itemId))
                        .thenRun(updatedCart -> cmd.replyTo.tell(StatusReply.success(updatedCart.toSummary())));
            } else {
                cmd.replyTo.tell(StatusReply.success(state.toSummary()));
                return Effect().none();
            }
        }

        Effect<ShoppingCartEvent, ShoppingCartState> onAdjustItemQuantity(ShoppingCartState state, ShoppingCartCommand.AdjustItemQuantity cmd) {
            if (cmd.quantity <= 0) {
                cmd.replyTo.tell(StatusReply.error("Quantity must be greater than zero"));
                return Effect().none();
            } else if (state.hasItem(cmd.itemId)) {
                return Effect().persist(new ShoppingCartEvent.ItemQuantityAdjusted(cartId, cmd.itemId, cmd.quantity))
                        .thenRun(updatedCart -> cmd.replyTo.tell(StatusReply.success(updatedCart.toSummary())));
            } else {
                cmd.replyTo.tell(StatusReply.error(
                        "Cannot adjust quantity for item '" + cmd.itemId + "'. Item not present on cart"));
                return Effect().none();
            }
        }

        Effect<ShoppingCartEvent, ShoppingCartState> onCheckout(ShoppingCartState state, ShoppingCartCommand.Checkout cmd) {
            if (state.isEmpty()) {
                cmd.replyTo.tell(StatusReply.error("Cannot checkout an empty shopping cart"));
                return Effect().none();
            } else {
                return Effect().persist(new ShoppingCartEvent.CheckedOut(cartId, Instant.now()))
                        .thenRun(updatedCart -> cmd.replyTo.tell(StatusReply.success(updatedCart.toSummary())));
            }
        }
    }

    private class CheckedOutCommandHandlers {
        Effect<ShoppingCartEvent, ShoppingCartState> onAddItem(ShoppingCartCommand.AddItem cmd) {
            cmd.replyTo.tell(StatusReply.error("Can't add an item to an already checked out shopping cart"));
            return Effect().none();
        }

        Effect<ShoppingCartEvent, ShoppingCartState> onRemoveItem(ShoppingCartCommand.RemoveItem cmd) {
            cmd.replyTo.tell(StatusReply.error("Can't remove an item from an already checked out shopping cart"));
            return Effect().none();
        }

        Effect<ShoppingCartEvent, ShoppingCartState> onAdjustItemQuantity(ShoppingCartCommand.AdjustItemQuantity cmd) {
            cmd.replyTo.tell(StatusReply.error("Can't adjust item on an already checked out shopping cart"));
            return Effect().none();
        }

        Effect<ShoppingCartEvent, ShoppingCartState> onCheckout(ShoppingCartCommand.Checkout cmd) {
            cmd.replyTo.tell(StatusReply.error("Can't checkout already checked out shopping cart"));
            return Effect().none();
        }
    }

    @Override
    public EventHandler<ShoppingCartState, ShoppingCartEvent> eventHandler() {
        return newEventHandlerBuilder().forAnyState()
                .onEvent(ShoppingCartEvent.ItemAdded.class, (state, event) -> state.updateItem(event.itemId, event.quantity))
                .onEvent(ShoppingCartEvent.ItemRemoved.class, (state, event) -> state.removeItem(event.itemId))
                .onEvent(ShoppingCartEvent.ItemQuantityAdjusted.class, (state, event) -> state.updateItem(event.itemId, event.quantity))
                .onEvent(ShoppingCartEvent.CheckedOut.class, (state, event) -> state.checkout(event.eventTime))
                .build();
    }

    @Override
    public RetentionCriteria retentionCriteria() {
        // enable snapshotting
        return RetentionCriteria.snapshotEvery(100, 3);
    }
}
