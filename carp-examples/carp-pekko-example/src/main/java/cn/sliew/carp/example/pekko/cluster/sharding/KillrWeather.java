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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.AddressFromURIString;
import org.apache.pekko.actor.typed.ActorSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main entry point for the application.
 * See the README.md for starting each node with sbt or maven
 */
public class KillrWeather {

    public static void main(String[] args) throws Exception {
        List<Integer> seedNodePorts =
                ConfigFactory.load().getStringList("pekko.cluster.seed-nodes")
                        .stream()
                        .map(AddressFromURIString::parse)
                        .map(addr -> (Integer) addr.port().get()) // Missing Java getter for port
                        .collect(Collectors.toList());

        // Either use a single port provided by the user
        // Or start each listed seed nodes port plus one node on a random port in this single JVM if the user
        // didn't provide args for the app
        // In a production application you wouldn't start multiple ActorSystem instances in the
        // same JVM, here we do it to simplify running a sample cluster from a single main method.
        List<Integer> ports = Arrays.stream(args).findFirst().map(str ->
                Collections.singletonList(Integer.parseInt(str))
        ).orElseGet(() -> {
            List<Integer> portsAndZero = new ArrayList<>(seedNodePorts);
            portsAndZero.add(0);
            return portsAndZero;
        });

        for (int port : ports) {
            final int httpPort;
            if (port > 0) httpPort = 10000 + port;  // offset from pekko port
            else httpPort = 0; // let OS decide

            Config config = configWithPort(port);
            ActorSystem.create(Guardian.create(httpPort), "KillrWeather", config);
        }
    }

    private static Config configWithPort(int port) {
        return ConfigFactory.parseMap(
                Collections.singletonMap("pekko.remote.artery.canonical.port", Integer.toString(port))
        ).withFallback(ConfigFactory.load());
    }

}