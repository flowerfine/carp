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

package cn.sliew.carp.example.mongo.controller;

import cn.sliew.carp.example.mongo.entity.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@RestController
@RequestMapping("/api/carp/example/mongo/template")
@Tag(name = "测试模块-MongoTemplate")
public class MongoTemplateController {

    @Autowired
    private MongoOperations mongoOperations;

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody Person param) {
        mongoOperations.insert(param);
        return true;
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean updatePersion(@Valid @RequestBody Person param) {
        mongoOperations.updateFirst(query(where("id").is(param.getId())), update("age", param.getAge()), Person.class);
        return true;
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") String id) {
        mongoOperations.remove(query(where("id").is(id)), Person.class);
        return true;
    }
}
