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

package cn.sliew.carp.module.kubernetes.watch.source.watch;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import lombok.extern.slf4j.Slf4j;

/**
 * Watcher for resources in Kubernetes.
 */
@Slf4j
@Deprecated
public abstract class AbstractKubernetesWatcher<T extends HasMetadata> implements Watcher<T> {

    protected final WatchCallbackHandler<T> callbackHandler;

    public AbstractKubernetesWatcher(WatchCallbackHandler<T> callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void onClose(WatcherException cause) {
        // null means the watcher is closed normally.
        if (cause == null) {
            log.info("The watcher is closing.");
        } else {
            // Fabric8 Kubernetes client will directly close the watcher when received a HTTP_GONE
            // status code, so this should be handled by the caller. Refer to
            // https://github.com/fabric8io/kubernetes-client/blob/v4.9.2/kubernetes-client/src/main/java/io/fabric8/kubernetes/client/dsl/internal/WatchConnectionManager.java#L255
            // for more information about the implementation.
            if (cause.isHttpGone()) {
                log.debug(
                        "Got a http code 'HTTP_GONE' which means the Kubernetes client has the "
                                + "too old resource version.",
                        cause);
                callbackHandler.handleError(new KubernetesTooOldResourceVersionException(cause));
            } else {
                callbackHandler.handleError(cause);
            }
        }
    }
}