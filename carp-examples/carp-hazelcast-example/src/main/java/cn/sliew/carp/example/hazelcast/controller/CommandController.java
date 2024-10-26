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

package cn.sliew.carp.example.hazelcast.controller;

import cn.sliew.carp.example.hazelcast.service.HazelcastMapService;
import com.hazelcast.map.IMap;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carp/example/hazelcast")
@Tag(name = "测试模块-Hazelcast")
public class CommandController {

    @Autowired
    private HazelcastMapService mapService;

    @PostMapping("/put")
    public boolean put(@RequestParam("key") String key, @RequestParam("value") String value) {
        mapService.getMap("map-command").put(key, value);
        IMap<String, String> map = mapService.getMap("");
        return true;
    }

    @GetMapping("/get")
    public String get(@RequestParam("key") String key) {
        return mapService.getMap("map-command").get(key);
    }
}