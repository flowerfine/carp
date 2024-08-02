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

import cn.sliew.carp.module.application.vela.api.v1.model.*;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1AddProjectUserRequest;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProjectApi {

    @RequestMapping(value = "/api/v1/projects/{projectName}/distributions")
    ResponseEntity<V1ListConfigDistributionResponse> listDistributions(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/distributions")
    ResponseEntity<V1EmptyResponse> applyDistribution(@PathVariable("projectName") String projectName, @RequestBody V1CreateConfigDistributionRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/distributions/{distributionName}")
    ResponseEntity<V1EmptyResponse> deleteDistribution(@PathVariable("projectName") String projectName, @PathVariable("distributionName") String distributionName);

    @RequestMapping(value = "/api/v1/projects")
    ResponseEntity<V1ListProjectResponse> listProjects();

    @RequestMapping(value = "/api/v1/projects/{projectName}")
    ResponseEntity<V1ProjectBase> detailProject(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects")
    ResponseEntity<V1ProjectBase> createProject(@RequestBody V1CreateProjectRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}")
    ResponseEntity<V1ProjectBase> updateProject(@PathVariable("projectName") String projectName, @RequestBody V1UpdateProjectRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}")
    ResponseEntity<V1EmptyResponse> deleteProject(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/configs")
    ResponseEntity<V1ListConfigResponse> getProjectConfigs(@PathVariable("projectName") String projectName, @RequestParam(value = "template", required = false) String template);

    @RequestMapping(value = "/api/v1/projects/{projectName}/configs/{configName}")
    ResponseEntity<V1Config> detailConfig(@PathVariable("projectName") String projectName, @PathVariable("configName") String configName, @RequestBody V1UpdateConfigRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/configs")
    ResponseEntity<V1Config> createProjectConfig(@PathVariable("projectName") String projectName, @RequestBody V1CreateConfigRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/configs/{configName}")
    ResponseEntity<V1EmptyResponse> deleteProjectConfig(@PathVariable("projectName") String projectName, @PathVariable("configName") String configName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/permissions")
    ResponseEntity<List<V1PermissionBase>> listProjectPermissions(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/permissions")
    ResponseEntity<List<V1PermissionBase>> createProjectPermission(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/configs/{configName}")
    ResponseEntity<V1Config> updateProjectConfig(@PathVariable("projectName") String projectName, @PathVariable("configName") String configName, @RequestBody V1UpdateConfigRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/permissions/{permissionName}")
    ResponseEntity<List<V1PermissionBase>> deleteProjectPermission(@PathVariable("projectName") String projectName, @PathVariable("permissionName") String permissionName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/roles")
    ResponseEntity<V1ListRolesResponse> listProjectRoles(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/roles")
    ResponseEntity<V1RoleBase> createProjectRole(@PathVariable("projectName") String projectName, @RequestBody V1CreateRoleRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/roles/{roleName}")
    ResponseEntity<V1RoleBase> updateProjectRole(@PathVariable("projectName") String projectName, @PathVariable("roleName") String roleName, @RequestBody V1UpdateRoleRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/roles/{roleName}")
    ResponseEntity<V1EmptyResponse> deleteProjectRole(@PathVariable("projectName") String projectName, @PathVariable("roleName") String roleName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/users")
    ResponseEntity<V1ListProjectUsersResponse> listProjectUser(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/users")
    ResponseEntity<V1ProjectUserBase> createProjectUser(@PathVariable("projectName") String projectName, @RequestBody V1AddProjectUserRequest body);

    @RequestMapping(value = "/api/v1/projects/{projectName}/users/{userName}")
    ResponseEntity<V1ProjectUserBase> updateProjectUser(@RequestBody V1UpdateProjectUserRequest body, @PathVariable("projectName") String projectName, @PathVariable("userName") String userName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/users/{userName}")
    ResponseEntity<V1EmptyResponse> deleteProjectUser(@RequestBody V1UpdateProjectUserRequest body, @PathVariable("projectName") String projectName, @PathVariable("userName") String userName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/config_templates")
    ResponseEntity<V1ListConfigTemplateResponse> getConfigTemplates(@PathVariable("projectName") String projectName, @RequestParam(value = "namespace", required = true) String namespace);

    @RequestMapping(value = "/api/v1/projects/{projectName}/config_templates/{templateName}")
    ResponseEntity<V1ConfigTemplateDetail> getConfigTemplateByTemplateName(@PathVariable("projectName") String projectName, @PathVariable("templateName") String templateName, @RequestParam(value = "namespace", required = false) String namespace);

    @RequestMapping(value = "/api/v1/projects/{projectName}/providers")
    ResponseEntity<V1ListTerraformProviderResponse> getProviders(@PathVariable("projectName") String projectName);

    @RequestMapping(value = "/api/v1/projects/{projectName}/targets")
    ResponseEntity<V1EmptyResponse> listProjectTargets(@PathVariable("projectName") String projectName);

}
