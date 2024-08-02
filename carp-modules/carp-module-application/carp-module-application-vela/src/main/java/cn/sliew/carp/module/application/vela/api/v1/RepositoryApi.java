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

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ChartVersionListResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ImageInfo;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ListImageRegistryResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.V1Properties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RepositoryApi {

    @GetMapping(value = "/api/v1/repository/charts")
    ResponseEntity<List<String>> listCharts(@RequestParam("project") String project, @RequestParam("repoUrl") String repoUrl, @RequestParam("secretName") String secretName);

    @GetMapping(value = "/api/v1/repository/charts/{chart}/versions")
    ResponseEntity<V1ChartVersionListResponse> listChartVersions(@RequestParam("project") String project, @PathVariable("chart") String chart, @RequestParam("repoUrl") String repoUrl, @RequestParam("secretName") String secretName);

    @GetMapping(value = "/api/v1/repository/charts/{chart}/versions/{version}/values")
    ResponseEntity<V1Properties> getChartValues(@RequestParam("project") String project, @PathVariable("chart") String chart, @PathVariable("version") String version, @RequestParam("repoUrl") String repoUrl, @RequestParam("secretName") String secretName);

    @GetMapping(value = "/api/v1/repository/chart/versions")
    ResponseEntity<V1ChartVersionListResponse> listVersionsFromQuery(@RequestParam("project") String project, @RequestParam("chart") String chart, @RequestParam("repoUrl") String repoUrl, @RequestParam("secretName") String secretName);

    @GetMapping(value = "/api/v1/repository/chart/values")
    ResponseEntity<String> chartValues(@RequestParam("project") String project, @RequestParam("chart") String chart, @RequestParam("version") String version, @RequestParam("repoUrl") String repoUrl, @RequestParam("repoType") String repoType, @RequestParam("secretName") String secretName);

    @GetMapping(value = "/api/v1/repository/image/info")
    ResponseEntity<V1ImageInfo> getImageInfo(@RequestParam("project") String project, @RequestParam("name") String name, @RequestParam("secretName") String secretName);

    @GetMapping(value = "/api/v1/repository/image/repos")
    ResponseEntity<V1ListImageRegistryResponse> getImageRepos(@RequestParam("project") String project);

    @GetMapping(value = "/api/v1/repository/chart_repos")
    ResponseEntity<List<String>> listRepo(@RequestParam("project") String project);

}
