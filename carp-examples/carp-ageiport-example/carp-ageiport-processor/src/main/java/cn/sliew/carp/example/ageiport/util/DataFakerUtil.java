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

import java.math.BigDecimal;

public enum DataFakerUtil {
    ;

    private static final Faker faker = new Faker();

    public static UserData generate() {
        UserData data = new UserData();
        data.setName(faker.name().fullName());
        data.setGender(faker.gender().types());
        data.setAge(BigDecimal.valueOf(faker.number().numberBetween(1, 100)));
        data.setGroupIndex(faker.number().numberBetween(1, 3));
        return data;
    }


}
