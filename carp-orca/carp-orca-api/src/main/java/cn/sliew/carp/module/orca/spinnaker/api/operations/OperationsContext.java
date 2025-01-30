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
package cn.sliew.carp.module.orca.spinnaker.api.operations;

/**
 * A placeholder for the result of {@link OperationsRunner#run} invocations.
 *
 * <p>For example, this may be a completed operation, or perhaps it's the ID of a running operation
 * - this would depend on the underlying operation that was invoked via the {@link OperationsRunner}
 * implementation.
 */
public interface OperationsContext {

    /**
     * They operation context key. This may be used to identify the operations context value at a
     * later point in time.
     */
    String contextKey();

    /**
     * The value of the operations context. May be an ID if the system is tracking the operations
     * execution asynchronously, or it may be the result value of the operation.
     */
    OperationsContextValue contextValue();

    /**
     * Marker interface for {@link OperationsContext#contextValue()} return value.
     */
    interface OperationsContextValue {
    }
}
