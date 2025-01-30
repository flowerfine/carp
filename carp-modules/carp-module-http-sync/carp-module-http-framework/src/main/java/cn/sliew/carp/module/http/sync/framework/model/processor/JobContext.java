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
package cn.sliew.carp.module.http.sync.framework.model.processor;

import cn.sliew.carp.module.http.sync.framework.model.JobSetting;
import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.job.JobLogLevel;
import cn.sliew.carp.module.http.sync.framework.model.manager.LockManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SplitManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SyncOffsetManager;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.pekko.actor.typed.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public interface JobContext {

    JobSetting jobSetting();

    JobInfo jobInfo();

    JobLogLevel logLevel();

    String dispatcher();

    ActorSystem actorSystem();

    MeterRegistry meterRegistry();

    SyncOffsetManager syncOffsetManager();

    SplitManager splitManager();

    LockManager lockManager();

    void log(Logger log, Level level, String message, Object... params);

    // 时间通知机制
}
