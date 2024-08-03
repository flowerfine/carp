///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package cn.sliew.carp.example.pekko.persistence;
//
//import cn.sliew.carp.framework.pekko.serialization.CborSerializable;
//import org.apache.pekko.actor.typed.ActorRef;
//import org.apache.pekko.actor.typed.Behavior;
//import org.apache.pekko.actor.typed.javadsl.ActorContext;
//import org.apache.pekko.actor.typed.javadsl.Behaviors;
//import org.apache.pekko.persistence.typed.PersistenceId;
//import org.apache.pekko.persistence.typed.state.javadsl.CommandHandlerWithReply;
//import org.apache.pekko.persistence.typed.state.javadsl.DurableStateBehaviorWithEnforcedReplies;
//
//public class MyPersistenceCounter extends DurableStateBehaviorWithEnforcedReplies<MyPersistenceCounter.Command<?>, MyPersistenceCounter.State> {
//
//    interface Command<ReplyMessage> extends CborSerializable {
//    }
//
//    public enum Increment implements Command<Void> {
//        INSTANCE
//    }
//
//    public static class IncrementBy implements Command<Void> {
//        public final int value;
//
//        public IncrementBy(int value) {
//            this.value = value;
//        }
//    }
//
//    public static class GetValue implements Command<State> {
//        private final ActorRef<Integer> replyTo;
//
//        public GetValue(ActorRef<Integer> replyTo) {
//            this.replyTo = replyTo;
//        }
//    }
//
//    public enum Delete implements Command<Void> {
//        INSTANCE
//    }
//
//    public static class State {
//        private final int value;
//
//        public State(int value) {
//            this.value = value;
//        }
//
//        public int get() {
//            return value;
//        }
//    }
//
//    public static Behavior<Command<?>> create(PersistenceId persistenceId) {
//        return Behaviors.setup(ctx -> new MyPersistenceCounter(persistenceId, ctx));
//    }
//
//    private final ActorContext<Command<?>> context;
//
//    private MyPersistenceCounter(PersistenceId persistenceId, ActorContext<Command<?>> context) {
//        super(persistenceId);
//        this.context = context;
//    }
//
//    @Override
//    public State emptyState() {
//        return new State(0);
//    }
//
//    @Override
//    public CommandHandlerWithReply<Command<?>, State> commandHandler() {
//        return newCommandHandlerBuilder()
//                .forAnyState()
//                .onCommand(Increment.class, (state, command) -> Effect().persist(new State(state.get() + 1)))
//                .onCommand(IncrementBy.class, (state, command) -> Effect().persist(new State(state.get() + command.value)))
//                .onCommand(GetValue.class, (state, command) -> Effect().reply(command.replyTo, state.get()))
//                .onCommand(Delete.class, (state, command) -> Effect().persist(emptyState()).thenStop())
//                .build();
//    }
//}
