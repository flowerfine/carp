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

package cn.sliew.carp.module.kubernetes.watch.source;

import cn.sliew.carp.module.kubernetes.service.entity.VersionGroupKind;
import cn.sliew.milky.common.util.JacksonUtil;
import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.api.model.GenericKubernetesResourceList;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.informers.ExceptionHandler;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import org.apache.pekko.stream.Attributes;
import org.apache.pekko.stream.Outlet;
import org.apache.pekko.stream.SourceShape;
import org.apache.pekko.stream.stage.AbstractOutHandler;
import org.apache.pekko.stream.stage.GraphStage;
import org.apache.pekko.stream.stage.GraphStageLogic;
import org.apache.pekko.stream.stage.TimerGraphStageLogicWithLogging;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class K8sResourceSource extends GraphStage<SourceShape<GenericKubernetesResource>> {

    private static final Attributes DEFAULT_ATTRIBUTES = Attributes.name("KubernetesResourceSource");

    private final VersionGroupKind gvk;
    private final KubernetesClient kubernetesClient;
    private final Integer maxBufferSize = 100;

    private final Outlet<GenericKubernetesResource> out = Outlet.create("KubernetesResourceSource.out");
    private final SourceShape<GenericKubernetesResource> shape = SourceShape.of(out);

    public K8sResourceSource(VersionGroupKind gvk, KubernetesClient kubernetesClient) {
        this.gvk = gvk;
        this.kubernetesClient = kubernetesClient;
    }

    @Override
    public SourceShape<GenericKubernetesResource> shape() {
        return shape;
    }

    @Override
    public Attributes initialAttributes() {
        return DEFAULT_ATTRIBUTES;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) throws IOException {
        return new KubernetesTimerGraphStageLogic(shape);
    }

    private class KubernetesTimerGraphStageLogic extends TimerGraphStageLogicWithLogging implements ResourceEventHandler<GenericKubernetesResource>, ExceptionHandler {
        private final Queue<GenericKubernetesResource> buffer = new ArrayDeque<>();

        public KubernetesTimerGraphStageLogic(SourceShape<GenericKubernetesResource> shape) {
            super(shape);
            setHandler(out, new AbstractOutHandler() {
                @Override
                public void onPull() throws Exception {
                    if (!buffer.isEmpty()) {
                        pushHead();
                    }
                }
            });
        }

        @Override
        public void onTimer(Object timerKey) {
            if (!isClosed(out)) {
                doPoll();
                if (!buffer.isEmpty()) {
                    pushHead();
                }
            }
        }

        @Override
        public void preStart() throws Exception {
            // 启动 informer 监听
            MixedOperation<GenericKubernetesResource, GenericKubernetesResourceList, Resource<GenericKubernetesResource>> operation = kubernetesClient.genericKubernetesResources(gvk.getApiVersion(), gvk.getKind());
            if (StringUtils.hasText(gvk.getNamespace())) {
                NonNamespaceOperation<GenericKubernetesResource, GenericKubernetesResourceList, Resource<GenericKubernetesResource>> namespaceOperation = operation.inNamespace(gvk.getNamespace());
                if (StringUtils.hasText(gvk.getName())) {
                    namespaceOperation.withName(gvk.getName()).inform().addEventHandler(this).start();
                } else {
                    // fixme 未设置 exceptionhandler
                    namespaceOperation.inform().addEventHandler(this).start();
                }
            } else {
                operation.inAnyNamespace().inform().addEventHandler(this).start();
            }

            // 启动定时监听
//            scheduleWithFixedDelay("poll", Duration.ofSeconds(3L), Duration.ofSeconds(3L));
        }

        @Override
        public void postStop() {
            buffer.clear();
        }

        private void pushHead() {
            if (isAvailable(out)) {
                GenericKubernetesResource head = buffer.poll();
                if (head != null) {
                    push(out, head);
                }
            }
        }

        private void doPoll() {
            MixedOperation<GenericKubernetesResource, GenericKubernetesResourceList, Resource<GenericKubernetesResource>> operation = kubernetesClient.genericKubernetesResources(gvk.getApiVersion(), gvk.getKind());
            if (StringUtils.hasText(gvk.getNamespace())) {
                buffer.addAll(inNamespace(operation));
            } else {
                buffer.addAll(inAnyNamespace(operation));
            }
            if (buffer.size() > maxBufferSize) {
                failStage(new RuntimeException("Max event buffer size " + maxBufferSize + " reached for gvk: " + JacksonUtil.toJsonString(gvk)));
            }
        }

        private List<GenericKubernetesResource> inNamespace(MixedOperation<GenericKubernetesResource, GenericKubernetesResourceList, Resource<GenericKubernetesResource>> operation) {
            NonNamespaceOperation<GenericKubernetesResource, GenericKubernetesResourceList, Resource<GenericKubernetesResource>> namespaceOperation = operation.inNamespace(gvk.getNamespace());
            if (StringUtils.hasText(gvk.getName())) {
                return Collections.singletonList(namespaceOperation.withName(gvk.getName()).get());
            } else {
                return namespaceOperation.list().getItems();
            }
        }

        private List<GenericKubernetesResource> inAnyNamespace(MixedOperation<GenericKubernetesResource, GenericKubernetesResourceList, Resource<GenericKubernetesResource>> operation) {
            if (StringUtils.hasText(gvk.getName())) {
                failStage(new IllegalArgumentException("gvk must have namespace when name exists! gvk: " + JacksonUtil.toJsonString(gvk)));
                return Collections.emptyList();
            } else {
                return operation.list().getItems();
            }
        }

        @Override
        public boolean retryAfterException(boolean isStarted, Throwable t) {
            failStage(t);
            return false;
        }

        @Override
        public void onAdd(GenericKubernetesResource obj) {
            onEvent(obj);
        }

        @Override
        public void onUpdate(GenericKubernetesResource oldObj, GenericKubernetesResource newObj) {
            onEvent(newObj);
        }

        @Override
        public void onDelete(GenericKubernetesResource obj, boolean deletedFinalStateUnknown) {
            onEvent(obj);
        }

        private void onEvent(GenericKubernetesResource obj) {
            ObjectMeta metadata = obj.getMetadata();
            log().info("推送: {}: {}", metadata.getNamespace(), metadata.getName());
            buffer.add(obj);
            if (buffer.size() > maxBufferSize) {
                failStage(new RuntimeException("Max event buffer size " + maxBufferSize + " reached for gvk: " + JacksonUtil.toJsonString(gvk)));
            }
            // fixme 不会推送
            if (!buffer.isEmpty()) {
                pushHead();
            }
        }
    }

}
