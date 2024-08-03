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

public interface ApplicationApi {

    @GetMapping(value = "/api/v1/applications")
    ResponseEntity<V1ListApplicationResponse> listApplications(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "project", required = false) String project, @RequestParam(value = "env", required = false) String env, @RequestParam(value = "targetName", required = false) String targetName);

    @GetMapping(value = "/api/v1/applications/{appName}")
    ResponseEntity<V1DetailApplicationResponse> detailApplication(@PathVariable("appName") String appName);

    @GetMapping(value = "/api/v1/applications/{appName}/revisions")
    ResponseEntity<V1ListRevisionsResponse> listApplicationRevisions(@PathVariable("appName") String appName, @RequestParam(value = "envName", required = false) String envName, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping(value = "/api/v1/applications/{appName}/revisions/{revision}")
    ResponseEntity<V1DetailRevisionResponse> detailApplicationRevision(@PathVariable("appName") String appName, @PathVariable("revision") String revision);

    @PostMapping(value = "/api/v1/applications/{appName}/revisions/{revision}/rollback")
    ResponseEntity<V1ApplicationRollbackResponse> rollbackApplicationWithRevision(@PathVariable("appName") String appName, @PathVariable("revision") String revision);

    @PostMapping(value = "/api/v1/applications/{appName}/reset")
    ResponseEntity<V1AppResetResponse> resetAppToLatestRevision(@PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications/{appName}/compare")
    ResponseEntity<V1AppCompareResponse> compareApp(@RequestBody V1AppCompareReq body, @PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications")
    ResponseEntity<V1ApplicationBase> createApplication(@RequestBody V1CreateApplicationRequest body);

    @PutMapping(value = "/api/v1/applications/{appName}")
    ResponseEntity<V1ApplicationBase> updateApplication(@PathVariable("appName") String appName, @RequestBody V1UpdateApplicationRequest body);

    @DeleteMapping(value = "/api/v1/applications/{appName}")
    ResponseEntity<V1EmptyResponse> deleteApplication(@PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications/{appName}/dry-run")
    ResponseEntity<V1AppDryRunResponse> dryRunAppOrRevision(@RequestBody V1AppDryRunReq body, @PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications/{appName}/deploy")
    ResponseEntity<V1ApplicationDeployResponse> deployApplication(@PathVariable("appName") String appName, @RequestBody V1ApplicationDeployRequest body);

    @GetMapping(value = "/api/v1/applications/{appName}/status")
    ResponseEntity<V1ApplicationStatusListResponse> getApplicationStatusFromAllEnvs(@PathVariable("appName") String appName);

    @GetMapping(value = "/api/v1/applications/{appName}/envs/{envName}/status")
    ResponseEntity<V1ApplicationStatusResponse> getApplicationStatus(@PathVariable("appName") String appName, @PathVariable("envName") String envName);

    @GetMapping(value = "/api/v1/applications/{appName}/records")
    ResponseEntity<Void> listApplicationRecords(@PathVariable("appName") String appName);

    @GetMapping(value = "/api/v1/applications/{appName}/statistics")
    ResponseEntity<V1ApplicationStatisticsResponse> applicationStatistics(@PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications/{appName}/template")
    ResponseEntity<V1ApplicationTemplateBase> publishApplicationTemplate(@PathVariable("appName") String appName, @RequestBody V1CreateApplicationTemplateRequest body);

    @GetMapping(value = "/api/v1/applications/{appName}/envs")
    ResponseEntity<V1ListApplicationEnvBinding> listApplicationEnvs(@PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications/{appName}/envs")
    ResponseEntity<V1EnvBinding> createApplicationEnv(@PathVariable("appName") String appName, @RequestBody V1CreateApplicationEnvbindingRequest body);

    @PutMapping(value = "/api/v1/applications/{appName}/envs/{envName}")
    ResponseEntity<V1EnvBinding> updateApplicationEnv(@PathVariable("appName") String appName, @PathVariable("envName") String envName, @RequestBody V1PutApplicationEnvBindingRequest body);

    @DeleteMapping(value = "/api/v1/applications/{appName}/envs/{envName}")
    ResponseEntity<V1EmptyResponse> deleteApplicationEnv(@PathVariable("appName") String appName, @PathVariable("envName") String envName);

    @PostMapping(value = "/api/v1/applications/{appName}/envs/{envName}/recycle")
    ResponseEntity<V1EmptyResponse> recycleApplicationEnv(@PathVariable("appName") String appName, @PathVariable("envName") String envName);

    @PostMapping(value = "/api/v1/applications/{appName}/components/{compName}/traits")
    ResponseEntity<V1EmptyResponse> addApplicationTrait(@PathVariable("appName") String appName, @PathVariable("compName") String compName, @RequestBody V1CreateApplicationTraitRequest body);

    @PutMapping(value = "/api/v1/applications/{appName}/components/{compName}/traits/{traitType}")
    ResponseEntity<V1ApplicationTrait> updateApplicationTrait(@PathVariable("appName") String appName, @PathVariable("compName") String compName, @PathVariable("traitType") String traitType, @RequestBody V1UpdateApplicationTraitRequest body);

    @DeleteMapping(value = "/api/v1/applications/{appName}/components/{compName}/traits/{traitType}")
    ResponseEntity<V1ApplicationTrait> deleteApplicationTrait(@PathVariable("appName") String appName, @PathVariable("compName") String compName, @PathVariable("traitType") String traitType);

    @GetMapping(value = "/api/v1/applications/{appName}/policies")
    ResponseEntity<V1ListApplicationPolicy> listApplicationPolicies(@PathVariable("appName") String appName);

    @GetMapping(value = "/api/v1/applications/{appName}/policies/{policyName}")
    ResponseEntity<V1DetailPolicyResponse> detailApplicationPolicy(@PathVariable("appName") String appName, @PathVariable("policyName") String policyName);

    @PostMapping(value = "/api/v1/applications/{appName}/policies")
    ResponseEntity<V1PolicyBase> createApplicationPolicy(@PathVariable("appName") String appName, @RequestBody V1CreatePolicyRequest body);

    @PutMapping(value = "/api/v1/applications/{appName}/policies/{policyName}")
    ResponseEntity<V1DetailPolicyResponse> updateApplicationPolicy(@PathVariable("appName") String appName, @PathVariable("policyName") String policyName, @RequestBody V1UpdatePolicyRequest body);

    @DeleteMapping(value = "/api/v1/applications/{appName}/policies/{policyName}")
    ResponseEntity<V1EmptyResponse> deleteApplicationPolicy(@PathVariable("appName") String appName, @PathVariable("policyName") String policyName, @RequestParam(value = "force", required = false) Boolean force);

    @GetMapping(value = "/api/v1/applications/{appName}/triggers")
    ResponseEntity<V1ListApplicationTriggerResponse> listApplicationTriggers(@PathVariable("appName") String appName);

    @PostMapping(value = "/api/v1/applications/{appName}/triggers")
    ResponseEntity<V1ApplicationTriggerBase> createApplicationTrigger(@PathVariable("appName") String appName, @RequestBody V1CreateApplicationTriggerRequest body);

    @PutMapping(value = "/api/v1/applications/{appName}/triggers/{token}")
    ResponseEntity<V1ApplicationTriggerBase> updateApplicationTrigger(@PathVariable("appName") String appName, @PathVariable("token") String token);

    @DeleteMapping(value = "/api/v1/applications/{appName}/triggers/{token}")
    ResponseEntity<V1EmptyResponse> deleteApplicationTrigger(@PathVariable("appName") String appName, @PathVariable("token") String token);

    @GetMapping(value = "/api/v1/applications/{appName}/components")
    ResponseEntity<V1ComponentListResponse> listApplicationComponents(@PathVariable("appName") String appName, @RequestParam(value = "envName", required = false) String envName);

    @GetMapping(value = "/api/v1/applications/{appName}/components/{compName}")
    ResponseEntity<V1DetailComponentResponse> detailComponent(@PathVariable("appName") String appName, @PathVariable("compName") String compName);

    @PostMapping(value = "/api/v1/applications/{appName}/components")
    ResponseEntity<V1ComponentBase> createComponent(@PathVariable("appName") String appName, @RequestBody V1CreateComponentRequest body);

    @PutMapping(value = "/api/v1/applications/{appName}/components/{compName}")
    ResponseEntity<V1ComponentBase> updateComponent(@PathVariable("appName") String appName, @PathVariable("compName") String compName, @RequestBody V1UpdateApplicationComponentRequest body);

    @DeleteMapping(value = "/api/v1/applications/{appName}/components/{compName}")
    ResponseEntity<V1EmptyResponse> deleteComponent(@PathVariable("appName") String appName, @PathVariable("compName") String compName);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows")
    ResponseEntity<V1ListWorkflowResponse> listApplicationWorkflows(@PathVariable("appName") String appName);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}")
    ResponseEntity<V1DetailWorkflowResponse> detailWorkflow(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName);

    @PostMapping(value = "/api/v1/applications/{appName}/workflows")
    ResponseEntity<V1DetailWorkflowResponse> createOrUpdateApplicationWorkflow(@RequestBody V1CreateWorkflowRequest body, @PathVariable("appName") String appName);

    @PutMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}")
    ResponseEntity<V1DetailWorkflowResponse> updateWorkflow(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @RequestBody V1UpdateWorkflowRequest body);

    @DeleteMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}")
    ResponseEntity<V1SimpleResponse> deleteWorkflow(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records")
    ResponseEntity<V1ListWorkflowRecordsResponse> listWorkflowRecords(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping(value = "/api/v1/applications/{appName}/envs/{envName}/records")
    ResponseEntity<V1ListWorkflowRecordsResponse> listWorkflowRecordsFromEnv(@PathVariable("appName") String appName, @PathVariable("envName") String envName, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}")
    ResponseEntity<V1DetailWorkflowRecordResponse> detailWorkflowRecord(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}/inputs")
    ResponseEntity<V1GetPipelineRunInputResponse> getWorkflowRecordInputs(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record, @RequestParam(value = "step", required = true) String step);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}/outputs")
    ResponseEntity<V1GetPipelineRunOutputResponse> getWorkflowRecordOutputs(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record, @RequestParam(value = "step", required = true) String step);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}/logs")
    ResponseEntity<Void> getWorkflowRecordLogs(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record, @RequestParam(value = "step", required = true) String step);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}/resume")
    ResponseEntity<V1EmptyResponse> resumeWorkflowRecord(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record, @RequestParam(value = "step", required = false) String step);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}/rollback")
    ResponseEntity<V1WorkflowRecordBase> rollbackWorkflowRecord(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record, @RequestParam(value = "rollbackVersion", required = false) String rollbackVersion);

    @GetMapping(value = "/api/v1/applications/{appName}/workflows/{workflowName}/records/{record}/terminate")
    ResponseEntity<V1EmptyResponse> terminateWorkflowRecord(@PathVariable("appName") String appName, @PathVariable("workflowName") String workflowName, @PathVariable("record") String record);

}
