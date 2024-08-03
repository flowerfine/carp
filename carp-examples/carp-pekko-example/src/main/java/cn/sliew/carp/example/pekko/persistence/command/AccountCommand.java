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

import cn.sliew.carp.framework.pekko.serialization.CborSerializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.pattern.StatusReply;

import java.math.BigDecimal;

public interface AccountCommand extends CborSerializable {

    class CreateAccount implements AccountCommand {
        public final ActorRef<StatusReply<Done>> replyTo;

        @JsonCreator
        public CreateAccount(ActorRef<StatusReply<Done>> replyTo) {
            this.replyTo = replyTo;
        }
    }

    class Deposit implements AccountCommand {
        public final BigDecimal amount;
        public final ActorRef<StatusReply<Done>> replyTo;

        public Deposit(BigDecimal amount, ActorRef<StatusReply<Done>> replyTo) {
            this.replyTo = replyTo;
            this.amount = amount;
        }
    }

    class Withdraw implements AccountCommand {
        public final BigDecimal amount;
        public final ActorRef<StatusReply<Done>> replyTo;

        public Withdraw(BigDecimal amount, ActorRef<StatusReply<Done>> replyTo) {
            this.amount = amount;
            this.replyTo = replyTo;
        }
    }

    class GetBalance implements AccountCommand {
        public final ActorRef<CurrentBalance> replyTo;

        @JsonCreator
        public GetBalance(ActorRef<CurrentBalance> replyTo) {
            this.replyTo = replyTo;
        }
    }

    class CloseAccount implements AccountCommand {
        public final ActorRef<StatusReply<Done>> replyTo;

        @JsonCreator
        public CloseAccount(ActorRef<StatusReply<Done>> replyTo) {
            this.replyTo = replyTo;
        }
    }

    class CurrentBalance implements CborSerializable {
        public final BigDecimal balance;

        @JsonCreator
        public CurrentBalance(BigDecimal balance) {
            this.balance = balance;
        }
    }
}
