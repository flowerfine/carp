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

package cn.sliew.carp.example.jdframe;

import cn.sliew.carp.example.jdframe.util.DataFakerUtil;
import cn.sliew.milky.common.util.JacksonUtil;
import io.github.burukeyou.dataframe.iframe.JDFrame;
import io.github.burukeyou.dataframe.iframe.item.FI2;

import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<User> users = DataFakerUtil.generateList(10);
        System.out.println(JacksonUtil.toJsonString(users));

        JDFrame<FI2<Integer, List<User>>> groups = JDFrame.read(users)
                .whereGe(User::getAge, 10)
                .group(User::getAge)
                .sortAsc(FI2::C1);

        groups.stream().forEach(fi2 -> {
            Integer groupIndex = fi2.C1();
            List<User> userList = fi2.getC2();
            System.out.println("分组-" + groupIndex + ": " + JacksonUtil.toJsonString(userList));
        });

        groups.show();
    }
}
