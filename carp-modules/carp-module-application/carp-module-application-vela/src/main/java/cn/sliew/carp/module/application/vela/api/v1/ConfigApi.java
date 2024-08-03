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

import java.util.List;

public interface ConfigApi {

    @GetMapping(value = "/api/v1/configs")
    ResponseEntity<V1ListConfigResponse> getConfigs(@RequestParam("template") String template);

    @GetMapping(value = "/api/v1/configs/{configName}")
    ResponseEntity<List<V1Config>> getConfig(@PathVariable("configName") String configName);

    @PostMapping(value = "/api/v1/configs")
    ResponseEntity<V1Config> createConfig(@RequestBody V1CreateConfigRequest body);

    @PutMapping(value = "/api/v1/configs/{configName}")
    ResponseEntity<List<V1UpdateConfigRequest>> updateConfig(@PathVariable("configName") String configName);

    @DeleteMapping(value = "/api/v1/configs/{configName}")
    ResponseEntity<V1EmptyResponse> deleteConfig(@PathVariable("configName") String configName);

    @GetMapping(value = "/api/v1/config_templates")
    ResponseEntity<V1ListConfigTemplateResponse> listConfigTemplates();

    @GetMapping(value = "/api/v1/config_templates/{templateName}")
    ResponseEntity<V1ConfigTemplateDetail> getConfigTemplate(@PathVariable("templateName") String templateName, @RequestParam("namespace") String namespace);
}
