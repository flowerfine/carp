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

package cn.sliew.carp.example.redisson.service.job;

import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * 必须实现 Serializable
 */
public class EchoCallable implements Callable<String>, Serializable {

    @RInject
    private RedissonClient client;

    @RInject
    private String taskId;

    private String input;

    public EchoCallable(String input) {
        this.input = input;
    }

    @Override
    public String call() throws Exception {
        return String.format("%s: %s", taskId, input);
    }
}
