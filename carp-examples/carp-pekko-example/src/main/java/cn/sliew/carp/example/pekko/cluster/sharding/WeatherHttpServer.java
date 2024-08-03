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

package cn.sliew.carp.example.pekko.cluster.sharding;

import org.apache.pekko.actor.CoordinatedShutdown;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.javadsl.Adapter;
import org.apache.pekko.http.javadsl.ConnectHttp;
import org.apache.pekko.http.javadsl.Http;
import org.apache.pekko.http.javadsl.server.Route;
import org.apache.pekko.stream.Materializer;
import org.apache.pekko.stream.SystemMaterializer;

import java.net.InetSocketAddress;
import java.time.Duration;

import static org.apache.pekko.Done.done;

final class WeatherHttpServer {

    public static void start(Route routes, int port, ActorSystem<?> system) {
        org.apache.pekko.actor.ActorSystem classicActorSystem = Adapter.toClassic(system);

        Materializer materializer = SystemMaterializer.get(system).materializer();

        Http.get(classicActorSystem).bindAndHandle(
                routes.flow(classicActorSystem, materializer),
                ConnectHttp.toHost("localhost", port),
                materializer
        ).whenComplete((binding, failure) -> {
            if (failure == null) {
                final InetSocketAddress address = binding.localAddress();
                system.log().info(
                        "WeatherServer online at http://{}:{}/",
                        address.getHostString(),
                        address.getPort());

                CoordinatedShutdown.get(classicActorSystem).addTask(
                        CoordinatedShutdown.PhaseServiceRequestsDone(),
                        "http-graceful-terminate",
                        () ->
                                binding.terminate(Duration.ofSeconds(10)).thenApply(terminated -> {
                                    system.log().info("WeatherServer http://{}:{}/ graceful shutdown completed",
                                            address.getHostString(),
                                            address.getPort()
                                    );
                                    return done();
                                })
                );
            } else {
                system.log().error("Failed to bind HTTP endpoint, terminating system", failure);
                system.terminate();
            }
        });
    }
}
