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

package cn.sliew.carp.framework.common.reflection.instructions;

import cn.sliew.carp.framework.common.reflection.JobDetailsBuilder;

public abstract class AbstractJVMInstruction {

    public static final Object DO_NOT_PUT_ON_STACK = new Object();

    protected final JobDetailsBuilder jobDetailsBuilder;

    protected AbstractJVMInstruction(JobDetailsBuilder jobDetailsBuilder) {
        this.jobDetailsBuilder = jobDetailsBuilder;
    }

    public abstract Object invokeInstruction();

    public void invokeInstructionAndPushOnStack() {
        Object result = invokeInstruction();
        if (result != DO_NOT_PUT_ON_STACK) {
            jobDetailsBuilder.getStack().add(result);
        }
    }
}
