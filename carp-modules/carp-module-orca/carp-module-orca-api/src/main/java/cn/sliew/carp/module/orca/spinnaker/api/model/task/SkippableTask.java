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
package cn.sliew.carp.module.orca.spinnaker.api.model.task;

/**
 * A skippable task can be configured via properties to go directly from NOT_STARTED to SKIPPED. By
 * default, the property name is:
 *
 * <p>tasks.$taskId.enabled
 *
 * <p>where `taskId` corresponds to the simple class name (without the package) with a lower case
 * first character. For example, a skippable class `com.foo.DummySkippableTask` could be disabled
 * via property
 *
 * <p>tasks.dummySkippableTask.enabled
 *
 * @see StartTaskHandler
 */
public interface SkippableTask extends Task {
    static String isEnabledPropertyName(String name) {
        String loweredName = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        return String.format("tasks.%s.enabled", loweredName);
    }

    default String isEnabledPropertyName() {
        return isEnabledPropertyName(
                getClass().getSimpleName().isBlank() ? getClass().getName() : getClass().getSimpleName());
    }
}
