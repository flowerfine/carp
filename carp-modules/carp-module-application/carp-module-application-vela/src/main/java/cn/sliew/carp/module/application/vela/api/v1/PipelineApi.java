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

package cn.sliew.carp.module.application.vela.api.v1;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface PipelineApi {

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/contexts")
    ResponseEntity<V1ListContextValueResponse> listContextValues(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName);

    @PostMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/contexts")
    ResponseEntity<V1Context> createContextValue(@RequestBody V1CreateContextValuesRequest body, @PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName);

    @PutMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/contexts/{contextName}")
    ResponseEntity<V1Context> updateContextValue(@RequestBody V1UpdateContextValuesRequest body, @PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("contextName") String contextName);

    @DeleteMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/contexts/{contextName}")
    ResponseEntity<V1ContextNameResponse> deleteContextValue(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("contextName") String contextName);

    @GetMapping(value = "/api/v1/pipelines")
    ResponseEntity<V1ListPipelineResponse> listPipelines(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "projectName", required = false) String projectName, @RequestParam(value = "detailed", required = false, defaultValue = "true") Boolean detailed);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}")
    ResponseEntity<V1GetPipelineResponse> getPipeline(@PathVariable("pipelineName") String pipelineName, @PathVariable("projectName") String projectName);

    @PostMapping(value = "/api/v1/projects/{projectName}/pipelines")
    ResponseEntity<V1PipelineBase> createPipeline(@RequestBody V1CreatePipelineRequest body, @PathVariable("projectName") String projectName);

    @PutMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}")
    ResponseEntity<V1PipelineBase> updatePipeline(@RequestBody V1UpdatePipelineRequest body, @PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName);

    @DeleteMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}")
    ResponseEntity<V1PipelineMetaResponse> deletePipeline(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs")
    ResponseEntity<V1ListPipelineRunResponse> listPipelineRuns(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @RequestParam(value = "status", required = false) String status);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}")
    ResponseEntity<V1PipelineRunBase> getPipelineRun(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/input")
    ResponseEntity<V1GetPipelineRunInputResponse> getPipelineRunInput(@RequestParam(value = "step", required = true) String step, @PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/output")
    ResponseEntity<V1GetPipelineRunOutputResponse> getPipelineRunOutput(@RequestParam(value = "step", required = true) String step, @PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/log")
    ResponseEntity<V1GetPipelineRunLogResponse> getPipelineRunLog(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName, @RequestParam(value = "step", required = false) String step);

    @GetMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/status")
    ResponseEntity<V1alpha1WorkflowRunStatus> getPipelineRunStatus(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

    @PostMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/run")
    ResponseEntity<V1PipelineRun> runPipeline(@RequestBody V1RunPipelineRequest body, @PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName);

    @PostMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/stop")
    ResponseEntity<V1PipelineRunMeta> stopPipeline(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

    @PostMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/resume")
    ResponseEntity<V1EmptyResponse> resumePipelineRun(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName, @RequestParam(value = "step", required = false) String step);

    @PostMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}/terminate")
    ResponseEntity<V1EmptyResponse> terminatePipelineRun(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

    @DeleteMapping(value = "/api/v1/projects/{projectName}/pipelines/{pipelineName}/runs/{runName}")
    ResponseEntity<V1PipelineRunMeta> deletePipelineRun(@PathVariable("projectName") String projectName, @PathVariable("pipelineName") String pipelineName, @PathVariable("runName") String runName);

}
