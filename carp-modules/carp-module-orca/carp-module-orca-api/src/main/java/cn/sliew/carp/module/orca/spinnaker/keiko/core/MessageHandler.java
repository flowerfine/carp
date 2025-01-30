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
package cn.sliew.carp.module.orca.spinnaker.keiko.core;

public interface MessageHandler<M extends Message> {

    Class<M> getMessageType();

    Queue getQueue();

    default void invoke(Message message) {
        if (getMessageType().isAssignableFrom(message.getClass())) {
            handle((M) message);
        } else {
            throw new IllegalArgumentException("Unsupported message type ${message.javaClass.simpleName}");
        }
    }

    void handle(M message);
}
