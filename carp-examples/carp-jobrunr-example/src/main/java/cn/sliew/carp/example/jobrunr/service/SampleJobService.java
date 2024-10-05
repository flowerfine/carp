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

package cn.sliew.carp.example.jobrunr.service;

import cn.sliew.carp.example.jobrunr.service.param.SampleJobInput;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.annotations.Recurring;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleJobService {

    @Recurring(id = "my-recurring-job", cron = "*/2 * * * *")
    @Job(name = "A recurring job")
    public void recurringJob() throws InterruptedException {
        log.info("The recurring job has begun.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("Error while executing recurring job", e);
            throw e;
        } finally {
            log.info("Recurring job has finished...");
        }
    }

    @Job(name = "The sample job with variable %0", retries = 2)
    public void executeSampleJob(String input) throws InterruptedException {
        log.info("The sample job has begun. The variable you passed is {}", input);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error("Error while executing sample job", e);
            throw e;
        } finally {
            log.info("Sample job has finished...");
        }
    }

    @Job
    public void sampleJobWithRecordInput(SampleJobInput sampleJobInput) throws InterruptedException {
        log.info("The sample job has begun. The variable you passed is {}", sampleJobInput.id());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error("Error while executing sample job", e);
            throw e;
        } finally {
            log.info("Sample job has finished...");
        }
    }
}