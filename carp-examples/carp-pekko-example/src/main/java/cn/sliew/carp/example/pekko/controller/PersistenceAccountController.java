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

import cn.sliew.carp.example.pekko.persistence.Account;
import cn.sliew.carp.example.pekko.persistence.command.AccountCommand;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.Props;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.apache.pekko.actor.typed.javadsl.AskPattern;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.pattern.StatusReply;
import org.apache.pekko.persistence.typed.PersistenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/carp/example/pekko/persistence/accounts")
@Tag(name = "测试模块-Pekko-Persistence-账号", description = "创建账号，转入、转出金额，查询金额")
public class PersistenceAccountController {

    @Autowired
    private ActorSystem system;

    private Duration timeout = Duration.ofSeconds(3);
    private Map<String, ActorRef<AccountCommand>> accounts = new HashMap<>();

    @PutMapping
    @Operation(summary = "创建账户", description = "创建账户")
    public CompletableFuture<String> create() {
        String accountNumber = UUIDUtil.randomUUId();
        PersistenceId persistenceId = PersistenceId.of("Account", accountNumber);
        CompletionStage<ActorRef<AccountCommand>> completionStage =
                AskPattern.ask(
                        system,
                        (ActorRef<ActorRef<AccountCommand>> replyTo) -> new SpawnProtocol.Spawn<>(Behaviors.setup(ctx -> Account.create(accountNumber, persistenceId)), "AccountActor", Props.empty(), replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.thenApply(actorRef -> {
            accounts.put(accountNumber, actorRef);
            CompletionStage<Done> createFuture =
                    AskPattern.askWithStatus(
                            actorRef,
                            (ActorRef<StatusReply<Done>> replyTo) -> new AccountCommand.CreateAccount(replyTo),
                            timeout,
                            system.scheduler());
            return createFuture;
        }).handle((unused, throwable) -> {
           if (throwable != null) {
               throwable.printStackTrace();
               return throwable.getMessage();
           }
           return accountNumber;
        }).toCompletableFuture();
    }

    @GetMapping("{accountNumber}")
    @Operation(summary = "账户余额", description = "账户余额")
    public CompletableFuture<AccountCommand.CurrentBalance> get(@PathVariable("accountNumber") String accountNumber) {
        ActorRef<AccountCommand> account = accounts.get(accountNumber);
        CompletionStage<AccountCommand.CurrentBalance> completionStage =
                AskPattern.ask(
                        account,
                        (ActorRef<AccountCommand.CurrentBalance> replyTo) -> new AccountCommand.GetBalance(replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.toCompletableFuture();
    }

    @PostMapping("{accountNumber}/deposit")
    @Operation(summary = "存钱", description = "存钱")
    public CompletableFuture<Void> deposit(@PathVariable("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount) {
        ActorRef<AccountCommand> account = accounts.get(accountNumber);
        CompletionStage<Done> completionStage =
                AskPattern.askWithStatus(
                        account,
                        (ActorRef<StatusReply<Done>> replyTo) -> new AccountCommand.Deposit(amount, replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.thenAccept(unused -> {}).toCompletableFuture();
    }

    @PostMapping("{accountNumber}/withdraw")
    @Operation(summary = "取钱", description = "取钱")
    public CompletableFuture<Void> withdraw(@PathVariable("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount) {
        ActorRef<AccountCommand> account = accounts.get(accountNumber);
        CompletionStage<Done> completionStage =
                AskPattern.askWithStatus(
                        account,
                        (ActorRef<StatusReply<Done>> replyTo) -> new AccountCommand.Withdraw(amount, replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.thenAccept(unused -> {}).toCompletableFuture();
    }

    @DeleteMapping("{accountNumber}")
    @Operation(summary = "关闭账户", description = "关闭账户")
    public CompletableFuture<Void> close(@PathVariable("accountNumber") String accountNumber) {
        ActorRef<AccountCommand> account = accounts.get(accountNumber);
        CompletionStage<Done> completionStage =
                AskPattern.askWithStatus(
                        account,
                        (ActorRef<StatusReply<Done>> replyTo) -> new AccountCommand.CloseAccount(replyTo),
                        timeout,
                        system.scheduler());

        return completionStage.thenAccept(unused -> {}).toCompletableFuture();
    }

}
