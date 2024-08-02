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

public interface ClusterApi {

    @GetMapping(value = "/api/v1/clusters")
    ResponseEntity<V1ListClusterResponse> listKubeClusters(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize);

    @GetMapping(value = "/api/v1/clusters/{clusterName}")
    ResponseEntity<V1DetailClusterResponse> getKubeCluster(@PathVariable("clusterName") String clusterName);

    @PostMapping(value = "/api/v1/clusters")
    ResponseEntity<V1ClusterBase> createKubeCluster(@RequestBody V1CreateClusterRequest body);

    @PutMapping(value = "/api/v1/clusters/{clusterName}")
    ResponseEntity<V1ClusterBase> modifyKubeCluster(@PathVariable("clusterName") String clusterName, @RequestBody V1CreateClusterRequest body);

    @DeleteMapping(value = "/api/v1/clusters/{clusterName}")
    ResponseEntity<V1ClusterBase> deleteKubeCluster(@PathVariable("clusterName") String clusterName);

    @PostMapping(value = "/api/v1/clusters/{clusterName}/namespaces")
    ResponseEntity<V1CreateClusterNamespaceResponse> createNamespace(@PathVariable("clusterName") String clusterName, @RequestBody V1CreateClusterNamespaceRequest body);

    @PostMapping(value = "/api/v1/clusters/cloud_clusters/{provider}")
    ResponseEntity<V1ListCloudClusterResponse> listCloudClusters(@PathVariable("provider") String provider, @RequestBody V1AccessKeyRequest body, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize);

    @PostMapping(value = "/api/v1/clusters/cloud_clusters/{provider}/create")
    ResponseEntity<V1CreateCloudClusterResponse> createCloudCluster(@PathVariable("provider") String provider, @RequestBody V1CreateCloudClusterRequest body);

    @GetMapping(value = "/api/v1/clusters/cloud_clusters/{provider}/creation")
    ResponseEntity<V1ListCloudClusterCreationResponse> listCloudClusterCreation(@PathVariable("provider") String provider);

    @GetMapping(value = "/api/v1/clusters/cloud_clusters/{provider}/creation/{cloudClusterName}")
    ResponseEntity<V1CreateCloudClusterResponse> getCloudClusterCreationStatus(@PathVariable("provider") String provider, @PathVariable("cloudClusterName") String cloudClusterName);

    @DeleteMapping(value = "/api/v1/clusters/cloud_clusters/{provider}/creation/{cloudClusterName}")
    ResponseEntity<V1CreateCloudClusterResponse> deleteCloudClusterCreation(@PathVariable("provider") String provider, @PathVariable("cloudClusterName") String cloudClusterName);

    @PostMapping(value = "/api/v1/clusters/cloud_clusters/{provider}/connect")
    ResponseEntity<V1ClusterBase> connectCloudCluster(@PathVariable("provider") String provider, @RequestBody V1ConnectCloudClusterRequest body);

}
