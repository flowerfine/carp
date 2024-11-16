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

package cn.sliew.carp.example.jobrunr.controller;

import cn.sliew.carp.example.jobrunr.service.SampleJobService;
import cn.sliew.carp.example.jobrunr.service.param.SampleJobInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/carp/example/jobrunr")
@Tag(name = "测试模块-JobRunr")
public class JobRunrController {

    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    private SampleJobService sampleService;

    @PutMapping("/enqueue-example-job")
    @Operation(summary = "新增", description = "新增")
    public String enqueueExampleJob(@RequestParam(value = "name", defaultValue = "World") String name) {
        JobId enqueuedJobId = jobScheduler.enqueue(() -> sampleService.executeSampleJob("Hello " + name));
        return "Job Enqueued: " + enqueuedJobId.toString();
    }

    @PutMapping("/enqueue-example-job-with-record")
    @Operation(summary = "新增-复杂对象", description = "新增-复杂对象")
    public String enqueueExampleJobWithRecord(@RequestParam(value = "name", defaultValue = "World") String name) {
        SampleJobInput sampleJobInput = new SampleJobInput(UUID.randomUUID(), name);
        final JobId enqueuedJobId = jobScheduler.enqueue(() -> sampleService.sampleJobWithRecordInput(sampleJobInput));
        return "Job Enqueued: " + enqueuedJobId.toString();
    }

    @PutMapping("/enqueue-example-job-pure-lambda")
    @Operation(summary = "新增-纯lambda", description = "新增-纯lambda")
    public String enqueueExampleJobPureLambda(@RequestParam(value = "name", defaultValue = "World") String name) {
        final JobId enqueuedJobId = jobScheduler.enqueue(() -> {
            // 只能写一个，如果打印 2 次就报错
            System.out.println("第一次：Hello " + name);
//            System.out.println("第二次：Hello " + name);
        });
        return "Job Enqueued: " + enqueuedJobId.toString();
    }

    @DeleteMapping("/delete-job")
    @Operation(summary = "删除", description = "删除")
    public String deleteExampleJob(@RequestParam(value = "id") String jobId) {
        jobScheduler.delete(UUID.fromString(jobId));
        return "Job deleted: " + jobId;
    }

    @PutMapping("/schedule-example-job")
    public String scheduleExampleJob(
            @RequestParam(value = "name", defaultValue = "World") String name,
            @RequestParam(value = "when", defaultValue = "PT3H") String when) {
        JobId scheduledJobId = jobScheduler.schedule(LocalDateTime.now().plus(Duration.parse(when)), () -> sampleService.executeSampleJob("Hello " + name));
        return "Job Scheduled: " + scheduledJobId.toString();
    }
}
