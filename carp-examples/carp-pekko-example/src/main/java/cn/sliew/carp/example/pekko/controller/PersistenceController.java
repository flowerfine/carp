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

package cn.sliew.carp.example.pekko.controller;

import cn.sliew.carp.example.pekko.persistence.ShoppingCart;
import cn.sliew.carp.example.pekko.persistence.command.ShoppingCartCommand;
import cn.sliew.carp.example.pekko.persistence.state.ShoppingCartSummary;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.Props;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.apache.pekko.actor.typed.javadsl.AskPattern;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.pattern.StatusReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/carp/example/pekko/persistence")
@Tag(name = "测试模块-Pekko-Persistence")
public class PersistenceController {

    @Autowired
    private ActorSystem system;

    private Duration timeout = Duration.ofSeconds(3);
    private Map<String, ActorRef<ShoppingCartCommand>> shoppingCarts = new HashMap<>();

    @PutMapping
    @Operation(summary = "创建购物车", description = "创建购物车")
    public String create() {
        String cartId = UUIDUtil.randomUUId();
        CompletionStage<ActorRef<ShoppingCartCommand>> completionStage =
                AskPattern.ask(
                        system,
                        (ActorRef<ActorRef<ShoppingCartCommand>> replyTo) -> new SpawnProtocol.Spawn<>(Behaviors.setup(ctx -> ShoppingCart.create(cartId)), "ShoppingCartActor", Props.empty(), replyTo),
                        timeout,
                        system.scheduler());

        completionStage.whenComplete((actorRef, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            } else {
                shoppingCarts.put(cartId, actorRef);
            }
        });
        return cartId;
    }

    @DeleteMapping("{cartId}")
    @Operation(summary = "关闭购物车", description = "关闭购物车")
    public CompletableFuture<ShoppingCartSummary> checkout(@PathVariable("cartId") String cartId) {
        ActorRef<ShoppingCartCommand> shoppingCart = shoppingCarts.get(cartId);
        CompletionStage<ShoppingCartSummary> completionStage =
                AskPattern.askWithStatus(
                        shoppingCart,
                        (ActorRef<StatusReply<ShoppingCartSummary>> replyTo) -> new ShoppingCartCommand.Checkout(replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.toCompletableFuture();
    }

    @GetMapping("{cartId}/items")
    @Operation(summary = "获取商品", description = "获取商品")
    public CompletableFuture<ShoppingCartSummary> get(@PathVariable("cartId") String cartId) {
        ActorRef<ShoppingCartCommand> shoppingCart = shoppingCarts.get(cartId);
        CompletionStage<ShoppingCartSummary> completionStage =
                AskPattern.ask(
                        shoppingCart,
                        (ActorRef<ShoppingCartSummary> replyTo) -> new ShoppingCartCommand.Get(replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.toCompletableFuture();
    }

    @PutMapping("{cartId}/items")
    @Operation(summary = "添加商品", description = "添加商品")
    public CompletableFuture<ShoppingCartSummary> addItem(@PathVariable("cartId") String cartId, @RequestParam("itemId") String itemId, @RequestParam("quantity") int quantity) {
        ActorRef<ShoppingCartCommand> shoppingCart = shoppingCarts.get(cartId);
        CompletionStage<ShoppingCartSummary> completionStage =
                AskPattern.askWithStatus(
                        shoppingCart,
                        (ActorRef<StatusReply<ShoppingCartSummary>> replyTo) -> new ShoppingCartCommand.AddItem(itemId, quantity, replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.toCompletableFuture();
    }

    @PostMapping("{cartId}/items/{itemId}")
    @Operation(summary = "更新商品", description = "更新商品")
    public CompletableFuture<ShoppingCartSummary> updateItem(@PathVariable("cartId") String cartId, @PathVariable("itemId") String itemId, @RequestParam("quantity") int quantity) {
        ActorRef<ShoppingCartCommand> shoppingCart = shoppingCarts.get(cartId);
        CompletionStage<ShoppingCartSummary> completionStage =
                AskPattern.askWithStatus(
                        shoppingCart,
                        (ActorRef<StatusReply<ShoppingCartSummary>> replyTo) -> new ShoppingCartCommand.AdjustItemQuantity(itemId, quantity, replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.toCompletableFuture();
    }

    @DeleteMapping("{cartId}/items/{itemId}")
    @Operation(summary = "删除商品", description = "删除商品")
    public CompletableFuture<ShoppingCartSummary> deleteItem(@PathVariable("cartId") String cartId, @PathVariable("itemId") String itemId) {
        ActorRef<ShoppingCartCommand> shoppingCart = shoppingCarts.get(cartId);
        CompletionStage<ShoppingCartSummary> completionStage =
                AskPattern.askWithStatus(
                        shoppingCart,
                        (ActorRef<StatusReply<ShoppingCartSummary>> replyTo) -> new ShoppingCartCommand.RemoveItem(itemId, replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.toCompletableFuture();
    }

}
