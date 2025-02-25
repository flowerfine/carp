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
package cn.sliew.carp.module.orca.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.id.config.CosIdConfig;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.orca.spinnaker.api.executions.ExecutionLauncher;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.WaitStage;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.log.pipeline.LogStage;
import de.huxhorn.sulky.ulid.ULID;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ahoo.cosid.IdGenerator;
import me.ahoo.cosid.provider.DefaultIdGeneratorProvider;
import me.ahoo.cosid.segment.SegmentId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.*;

@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/orca/pipeline")
@Tag(name = "编排模块-Orca测试")
public class OrcaExecutionController {

    private static final IdGenerator ID_GENERATOR = DefaultIdGeneratorProvider.INSTANCE.getShare();
    private static final ULID ULID_GENERATOR = new ULID();

    @Lazy
    @Autowired
    @Qualifier(CosIdConfig.SEGMENT_ID_BEAN)
    private SegmentId segmentId;
    @Autowired
    private ExecutionRepository executionRepository;
    @Autowired
    private ExecutionLauncher executionLauncher;

    @GetMapping("/list")
    @Operation(summary = "列表查询", description = "列表查询")
    public List<PipelineExecution> list() {
        Observable<PipelineExecution> observable = executionRepository.retrieve(ExecutionType.PIPELINE);
        return observable.subscribeOn(Schedulers.io()).toList().blockingGet();
    }

    @PutMapping("add")
    @Operation(summary = "新增", description = "新增")
    public PipelineExecution add() {
        return executionLauncher.start(ExecutionType.PIPELINE, buildPipelineMap());
    }

    @GetMapping("addLog")
    public PipelineExecution addLog() {
        return executionLauncher.start(ExecutionType.PIPELINE, buildLogPipelineMap());
    }

    private Map<String, Object> buildPipelineMap() {
        Map<String, Object> result = new HashMap<>();
        // id 没用
        result.put("id", segmentId.generate());
//        result.put("executionId", ID_GENERATOR.nextULID());
        result.put("namespace", "quoll-pipeline-example");
        result.put("name", "pipeline-test");
        List<Map<String, Object>> stages = new ArrayList<>();
        String log1Id = ULID_GENERATOR.nextULID();
        stages.add(buildLogStageMap(log1Id, Collections.emptyList(), SyntheticStageOwner.STAGE_BEFORE));
        String waitId = ULID_GENERATOR.nextULID();
        stages.add(buildWaitStageMap(waitId, Collections.singletonList(log1Id)));
        String log2Id = ULID_GENERATOR.nextULID();
        stages.add(buildLogStageMap(log2Id, Collections.singletonList(waitId), SyntheticStageOwner.STAGE_AFTER));
        result.put("stages", stages);
        return result;
    }

    private Map<String, Object> buildLogPipelineMap() {
        Map<String, Object> result = new HashMap<>();
        // id 没用
        result.put("id", segmentId.generate());
//        result.put("executionId", ID_GENERATOR.nextULID());
        result.put("namespace", "quoll-pipeline-example");
        result.put("name", "pipeline-test");
        List<Map<String, Object>> stages = new ArrayList<>();
        String log1Id = ULID_GENERATOR.nextULID();
        stages.add(buildLogStageMap(log1Id, Collections.emptyList(), SyntheticStageOwner.STAGE_BEFORE));
        result.put("stages", stages);
        return result;
    }

    private Map<String, Object> buildWaitStageMap(String id, List<String> requisiteStageRefIds) {
        Map<String, Object> stage = new HashMap<>();
        // 没用，id 系统内部会自动生成，无法设置
        stage.put("id", segmentId.generate());
        stage.put("refId", id);
        stage.put("type", WaitStage.STAGE_TYPE);
        stage.put("name", "wait");
        stage.put("requisiteStageRefIds", requisiteStageRefIds);
        // context 参数，参考 WaitStage.WaitStageContext
        stage.putAll(buildWaitStateContextMap());
        return stage;
    }

    private Map<String, Object> buildLogStageMap(String id, List<String> requisiteStageRefIds, SyntheticStageOwner syntheticStageOwner) {
        Map<String, Object> stage = new HashMap<>();
        // 没用，id 系统内部会自动生成，无法设置
        stage.put("id", segmentId.generate());
        stage.put("refId", id);
        stage.put("type", LogStage.STAGE_TYPE);
        stage.put("name", "log");
        stage.put("requisiteStageRefIds", requisiteStageRefIds);
        stage.put("syntheticStageOwner", syntheticStageOwner);
        // context 参数，参考 LogStage.StageData
        stage.put("context", buildLogStateContextMap());
        return stage;
    }

    private Map<String, Object> buildLogStateContextMap() {
        Map<String, Object> context = new HashMap<>();
        context.put("url", "url-data");
        context.put("payload", "payload-data");
        return context;
    }

    private Map<String, Object> buildWaitStateContextMap() {
        Map<String, Object> context = new HashMap<>();
        context.put("waitTime", Duration.ofSeconds(30).toSeconds());
        context.put("skipRemainingWait", false);
        return context;
    }
}
