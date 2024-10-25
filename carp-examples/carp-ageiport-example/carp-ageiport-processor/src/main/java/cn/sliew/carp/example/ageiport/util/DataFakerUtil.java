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

package cn.sliew.carp.example.ageiport.util;

import cn.sliew.carp.processor.core.model.UserData;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

import java.math.BigDecimal;

public enum DataFakerUtil {
    ;

    private static final Faker FAKER = new Faker();
    private static final JavaObjectTransformer J_TRANSFORMER = new JavaObjectTransformer();

    public static Schema<Object, ?> userDataSchema() {
        return Schema.of(
                Field.field("id", () -> FAKER.number().positive()),
                Field.field("name", () -> FAKER.name().fullName()),
                Field.field("gender", () -> FAKER.gender().types()),
                Field.field("age", () -> BigDecimal.valueOf(FAKER.number().numberBetween(1, 100))),
                Field.field("groupIndex", () -> FAKER.number().numberBetween(1, 3)),

                Field.field("manQuestion1", () -> FAKER.name().fullName()),
                Field.field("manQuestion2", () -> FAKER.name().fullName()),
                Field.field("womenQuestion1", () -> FAKER.name().fullName()),
                Field.field("womenQuestion2", () -> FAKER.name().fullName()),
                Field.field("otherQuestion1", () -> FAKER.name().fullName()),
                Field.field("otherQuestion2", () -> FAKER.name().fullName())
        );
    }

    public static UserData generate() {
        return (UserData) J_TRANSFORMER.apply(UserData.class, userDataSchema());
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }
}
