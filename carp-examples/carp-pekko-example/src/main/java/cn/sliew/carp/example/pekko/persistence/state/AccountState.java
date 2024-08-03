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
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public interface AccountState extends CborSerializable {

    class OpenedAccount implements AccountState {
        public final BigDecimal balance;

        public OpenedAccount() {
            this.balance = BigDecimal.ZERO;
        }

        @JsonCreator
        public OpenedAccount(BigDecimal balance) {
            this.balance = balance;
        }

        public OpenedAccount makeDeposit(BigDecimal amount) {
            return new OpenedAccount(balance.add(amount));
        }

        public boolean canWithdraw(BigDecimal amount) {
            return (balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0);
        }

        public OpenedAccount makeWithdraw(BigDecimal amount) {
            if (!canWithdraw(amount))
                throw new IllegalStateException("Account balance can't be negative");
            return new OpenedAccount(balance.subtract(amount));
        }

        public ClosedAccount closedAccount() {
            return new ClosedAccount();
        }
    }

    class ClosedAccount implements AccountState {
    }
}
