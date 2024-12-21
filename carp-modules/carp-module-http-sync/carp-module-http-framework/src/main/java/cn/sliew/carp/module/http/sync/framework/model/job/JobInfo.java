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
package cn.sliew.carp.module.http.sync.framework.model.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo {

    private String group;
    private String job;
    private String subJob;
    private String account;
    private String subAccount;

    public String getGroup() {
        return group;
    }

    public String getJob() {
        return job;
    }

    public Optional<String> getSubJob() {
        return Optional.ofNullable(subJob);
    }

    public Optional<String> getAccount() {
        return Optional.ofNullable(account);
    }

    public Optional<String> getSubAccount() {
        return Optional.ofNullable(subAccount);
    }
}
