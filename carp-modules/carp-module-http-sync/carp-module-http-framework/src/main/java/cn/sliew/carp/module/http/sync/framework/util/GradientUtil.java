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

package cn.sliew.carp.module.http.sync.framework.util;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public enum GradientUtil {
    ;

    public static final Duration MIN_GRADIENT = Duration.ofSeconds(1L);

    public static List<Duration> getDefaultGradients() {
        return Arrays.asList(
                Duration.ofMinutes(15L),
                Duration.ofMinutes(5L),
                Duration.ofMinutes(2L),
                Duration.ofMinutes(1L));
    }

    public static List<Duration> getSparseGradients() {
        return Arrays.asList(
                Duration.ofDays(1L),
                Duration.ofHours(12L),
                Duration.ofHours(6L),
                Duration.ofHours(3L),
                Duration.ofHours(1L),
                Duration.ofMinutes(30L)
        );
    }

    public static List<Duration> getMediumGradients() {
        return Arrays.asList(
                Duration.ofHours(12L),
                Duration.ofHours(6L),
                Duration.ofHours(3L),
                Duration.ofHours(1L)
        );
    }

    public static List<Duration> getSmallGradients() {
        return Arrays.asList(
                Duration.ofHours(1L),
                Duration.ofMinutes(30L)
        );
    }

    public static List<Duration> getDenseGradients() {
        return Arrays.asList(
                Duration.ofMinutes(1L)
        );
    }
}
