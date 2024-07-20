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

import org.apache.pekko.stream.Attributes;
import org.apache.pekko.stream.Outlet;
import org.apache.pekko.stream.SourceShape;
import org.apache.pekko.stream.stage.AbstractOutHandler;
import org.apache.pekko.stream.stage.GraphStage;
import org.apache.pekko.stream.stage.GraphStageLogic;
import org.apache.pekko.stream.stage.TimerGraphStageLogic;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class K8sResourceSource<T> extends GraphStage<SourceShape<T>> {

    private static final Attributes DEFAULT_ATTRIBUTES = Attributes.name("KubernetesResourceSource");

    public final Outlet<T> out = Outlet.create("KubernetesResourceSource.out");
    private final SourceShape<T> shape = SourceShape.of(out);


    @Override
    public SourceShape<T> shape() {
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

    private class KubernetesTimerGraphStageLogic extends TimerGraphStageLogic {
        private final Queue<T> buffer = new ArrayDeque<>();

        public KubernetesTimerGraphStageLogic(SourceShape<T> shape) {
            super(shape);

            setHandler(out, new AbstractOutHandler() {
                @Override
                public void onPull() throws Exception {
                    if (!buffer.isEmpty()) {
                        pushHead();
                    } else {
                        doPoll();
                        if (!buffer.isEmpty()) {
                            pushHead();
                        } else {
                            schedulePoll();
                        }
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
                } else {
                    schedulePoll();
                }
            }
        }

        @Override
        public void postStop() {

        }

        private void pushHead() {
            T head = buffer.poll();
            if (head != null) {
                push(out, head);
            }
        }

        private void schedulePoll() {
//            scheduleOnce("poll", pollInterval);
        }

        private void doPoll() {

        }

    }


}
