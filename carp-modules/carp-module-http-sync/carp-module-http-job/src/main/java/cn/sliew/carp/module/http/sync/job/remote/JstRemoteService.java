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

package cn.sliew.carp.module.http.sync.job.remote;

import cn.sliew.carp.module.http.sync.job.repository.entity.jst.JstAuth;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstAuthMapper;
import cn.sliew.carp.module.http.sync.remote.jst.api.JstOrderClient;
import cn.sliew.carp.module.http.sync.remote.jst.request.order.OrdersSingleQuery;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstNewResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.JstOrdersResult;
import cn.sliew.milky.common.util.JacksonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class JstRemoteService {

    private URI uri = URI.create("https://api.jushuitan.com/");

    @Autowired
    private JstAuthMapper jstAuthMapper;
    @Autowired
    private JstOrderClient jstOrderClient;

    private String sign(JstAuth auth, String param) {
        Map<String, String> params = new HashMap();
        params.put("app_key", auth.getAppKey());
        params.put("access_token", auth.getAccessToken());
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("version", "2");
        params.put("charset", "utf-8");
        params.put("biz", param);
//        String sign = ApiUtil.getSign(auth.getAppSecret(), params);
        String sign = null;
        params.put("sign", sign);
        return Joiner.on("&").withKeyValueSeparator("=").join(params);
    }

    private JstAuth getAuth(String appKey, String company) {
        LambdaQueryWrapper<JstAuth> queryWrapper = Wrappers.lambdaQuery(JstAuth.class)
                .eq(JstAuth::getAppKey, appKey)
                .eq(JstAuth::getCompany, company);
        return jstAuthMapper.selectOne(queryWrapper);
    }

    public JstNewResult<JstOrdersResult> getOrders(JstAuth auth, OrdersSingleQuery query) {
        return jstOrderClient.getOrders(uri, sign(auth, JacksonUtil.toJsonString(query)));
    }
}
