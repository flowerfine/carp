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

import cn.sliew.carp.example.pekko.persistence.command.AccountCommand;
import cn.sliew.carp.example.pekko.persistence.state.AccountState;
import org.apache.pekko.pattern.StatusReply;
import org.apache.pekko.persistence.typed.PersistenceId;
import org.apache.pekko.persistence.typed.state.javadsl.CommandHandlerWithReply;
import org.apache.pekko.persistence.typed.state.javadsl.CommandHandlerWithReplyBuilder;
import org.apache.pekko.persistence.typed.state.javadsl.DurableStateBehaviorWithEnforcedReplies;
import org.apache.pekko.persistence.typed.state.javadsl.ReplyEffect;

import java.math.BigDecimal;

public class Account extends DurableStateBehaviorWithEnforcedReplies<AccountCommand, AccountState> {

    public static Account create(String accountNumber, PersistenceId persistenceId) {
        return new Account(accountNumber, persistenceId);
    }

    private final String accountNumber;

    private Account(String accountNumber, PersistenceId persistenceId) {
        super(persistenceId);
        this.accountNumber = accountNumber;
    }

    @Override
    public AccountState emptyState() {
        return null;
    }

    @Override
    public CommandHandlerWithReply<AccountCommand, AccountState> commandHandler() {
        CommandHandlerWithReplyBuilder<AccountCommand, AccountState> builder = newCommandHandlerWithReplyBuilder();

        builder.forNullState().onCommand(AccountCommand.CreateAccount.class, this::createAccount);
        builder
                .forStateType(AccountState.OpenedAccount.class)
                .onCommand(AccountCommand.Deposit.class, this::deposit)
                .onCommand(AccountCommand.Withdraw.class, this::withdraw)
                .onCommand(AccountCommand.GetBalance.class, this::getBalance)
                .onCommand(AccountCommand.CloseAccount.class, this::closeAccount);
        builder
                .forStateType(AccountState.ClosedAccount.class)
                .onAnyCommand(() -> Effect().unhandled().thenNoReply());

        return builder.build();
    }

    private ReplyEffect<AccountState> createAccount(AccountCommand.CreateAccount command) {
        return Effect()
                .persist(new AccountState.OpenedAccount())
                .thenReply(command.replyTo, account2 -> StatusReply.ack());
    }

    private ReplyEffect<AccountState> deposit(AccountState.OpenedAccount account, AccountCommand.Deposit command) {
        return Effect()
                .persist(account.makeDeposit(command.amount))
                .thenReply(command.replyTo, account2 -> StatusReply.ack());
    }

    private ReplyEffect<AccountState> withdraw(AccountState.OpenedAccount account, AccountCommand.Withdraw command) {
        if (!account.canWithdraw(command.amount)) {
            return Effect()
                    .reply(
                            command.replyTo,
                            StatusReply.error("not enough funds to withdraw " + command.amount));
        } else {
            return Effect()
                    .persist(account.makeWithdraw(command.amount))
                    .thenReply(command.replyTo, account2 -> StatusReply.ack());
        }
    }

    private ReplyEffect<AccountState> getBalance(AccountState.OpenedAccount account, AccountCommand.GetBalance command) {
        return Effect().reply(command.replyTo, new AccountCommand.CurrentBalance(account.balance));
    }

    private ReplyEffect<AccountState> closeAccount(AccountState.OpenedAccount account, AccountCommand.CloseAccount command) {
        if (account.balance.equals(BigDecimal.ZERO)) {
            return Effect()
                    .persist(account.closedAccount())
                    .thenReply(command.replyTo, account2 -> StatusReply.ack());
        } else {
            return Effect()
                    .reply(command.replyTo, StatusReply.error("balance must be zero for closing account"));
        }
    }

}
