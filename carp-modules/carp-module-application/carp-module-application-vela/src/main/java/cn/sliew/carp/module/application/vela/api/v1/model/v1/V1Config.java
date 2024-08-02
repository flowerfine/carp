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

package cn.sliew.carp.module.application.vela.api.v1.model.v1;

import cn.sliew.carp.module.application.vela.api.v1.model.V1ConfigVolumes;
import cn.sliew.carp.module.application.vela.api.v1.model.V1HealthConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class V1Config {
    @JsonProperty("ArgsEscaped")
    private Boolean argsEscaped = null;

    @JsonProperty("AttachStderr")
    private Boolean attachStderr = null;

    @JsonProperty("AttachStdin")
    private Boolean attachStdin = null;

    @JsonProperty("AttachStdout")
    private Boolean attachStdout = null;

    @JsonProperty("Cmd")
    private List<String> cmd = null;

    @JsonProperty("Domainname")
    private String domainname = null;

    @JsonProperty("Entrypoint")
    private List<String> entrypoint = null;

    @JsonProperty("Env")
    private List<String> env = null;

    @JsonProperty("ExposedPorts")
    private Map<String, V1ConfigExposedPorts> exposedPorts = null;

    @JsonProperty("Healthcheck")
    private V1HealthConfig healthcheck = null;

    @JsonProperty("Hostname")
    private String hostname = null;

    @JsonProperty("Image")
    private String image = null;

    @JsonProperty("Labels")
    private Map<String, String> labels = null;

    @JsonProperty("MacAddress")
    private String macAddress = null;

    @JsonProperty("NetworkDisabled")
    private Boolean networkDisabled = null;

    @JsonProperty("OnBuild")
    private List<String> onBuild = null;

    @JsonProperty("OpenStdin")
    private Boolean openStdin = null;

    @JsonProperty("Shell")
    private List<String> shell = null;

    @JsonProperty("StdinOnce")
    private Boolean stdinOnce = null;

    @JsonProperty("StopSignal")
    private String stopSignal = null;

    @JsonProperty("Tty")
    private Boolean tty = null;

    @JsonProperty("User")
    private String user = null;

    @JsonProperty("Volumes")
    private Map<String, V1ConfigVolumes> volumes = null;

    @JsonProperty("WorkingDir")
    private String workingDir = null;

}

