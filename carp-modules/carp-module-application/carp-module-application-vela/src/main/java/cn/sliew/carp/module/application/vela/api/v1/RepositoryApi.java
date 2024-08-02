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

import cn.sliew.carp.module.application.vela.api.v1.model.V1ChartVersionListResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.V1ImageInfo;
import cn.sliew.carp.module.application.vela.api.v1.model.V1ListImageRegistryResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.V1Properties;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public interface RepositoryApi {


    @RequestMapping(value = "/api/v1/repository/chart/values",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<String> chartValues(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project, @NotNull @ApiParam(value = "helm chart", required = true) @Valid @RequestParam(value = "chart", required = true) String chart, @NotNull @ApiParam(value = "helm chart version", required = true) @Valid @RequestParam(value = "version", required = true) String version, @NotNull @ApiParam(value = "helm repository url", required = true) @Valid @RequestParam(value = "repoUrl", required = true) String repoUrl, @NotNull @ApiParam(value = "helm repository type", required = true) @Valid @RequestParam(value = "repoType", required = true) String repoType, @ApiParam(value = "secret of the repo") @Valid @RequestParam(value = "secretName", required = false) String secretName);


    @RequestMapping(value = "/api/v1/repository/charts/{chart}/versions/{version}/values",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<V1Properties> getChartValues(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project, @ApiParam(value = "identifier of the helm chart", required = true) @PathVariable("chart") String chart, @ApiParam(value = "version of the helm chart", required = true) @PathVariable("version") String version, @ApiParam(value = "helm repository url") @Valid @RequestParam(value = "repoUrl", required = false) String repoUrl, @ApiParam(value = "secret of the repo") @Valid @RequestParam(value = "secretName", required = false) String secretName);


    @RequestMapping(value = "/api/v1/repository/image/info",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<V1ImageInfo> getImageInfo(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project, @NotNull @ApiParam(value = "the image name", required = true) @Valid @RequestParam(value = "name", required = true) String name, @ApiParam(value = "the secret name of the image repository") @Valid @RequestParam(value = "secretName", required = false) String secretName);


    @RequestMapping(value = "/api/v1/repository/image/repos",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<V1ListImageRegistryResponse> getImageRepos(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project);


    @RequestMapping(value = "/api/v1/repository/charts/{chart}/versions",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<V1ChartVersionListResponse> listChartVersions(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project, @ApiParam(value = "identifier of the helm chart", required = true) @PathVariable("chart") String chart, @ApiParam(value = "helm repository url") @Valid @RequestParam(value = "repoUrl", required = false) String repoUrl, @ApiParam(value = "secret of the repo") @Valid @RequestParam(value = "secretName", required = false) String secretName);


    @RequestMapping(value = "/api/v1/repository/charts",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<List<String>> listCharts(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project, @ApiParam(value = "helm repository url") @Valid @RequestParam(value = "repoUrl", required = false) String repoUrl, @ApiParam(value = "secret of the repo") @Valid @RequestParam(value = "secretName", required = false) String secretName);


    @RequestMapping(value = "/api/v1/repository/chart_repos",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<List<String>> listRepo(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project);


    @RequestMapping(value = "/api/v1/repository/chart/versions",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<V1ChartVersionListResponse> listVersionsFromQuery(@NotNull @ApiParam(value = "the config project", required = true) @Valid @RequestParam(value = "project", required = true) String project, @NotNull @ApiParam(value = "helm chart", required = true) @Valid @RequestParam(value = "chart", required = true) String chart, @NotNull @ApiParam(value = "helm repository url", required = true) @Valid @RequestParam(value = "repoUrl", required = true) String repoUrl, @ApiParam(value = "secret of the repo") @Valid @RequestParam(value = "secretName", required = false) String secretName);


}
