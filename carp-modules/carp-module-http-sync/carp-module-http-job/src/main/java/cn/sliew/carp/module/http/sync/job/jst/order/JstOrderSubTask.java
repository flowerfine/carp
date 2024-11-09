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

package cn.sliew.carp.module.http.sync.job.jst.order;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.exception.SliewException;
import cn.sliew.carp.module.http.sync.framework.model.FetchResult;
import cn.sliew.carp.module.http.sync.framework.model.internal.SimpleJobContext;
import cn.sliew.carp.module.http.sync.job.jst.AbstractJstSubTask;
import cn.sliew.carp.module.http.sync.job.jst.util.JstResultWrapper;
import cn.sliew.carp.module.http.sync.job.jst.util.JstUtil;
import cn.sliew.carp.module.http.sync.job.remote.JstRemoteService;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstOrderMapper;
import cn.sliew.carp.module.http.sync.remote.jst.request.order.OrdersSingleQuery;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstNewResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.JstOrdersResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.OrdersSingleDO;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.japi.Pair;
import org.apache.pekko.stream.javadsl.Source;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class JstOrderSubTask extends AbstractJstSubTask<JstOrderRootTask, OrdersSingleQuery, JstOrdersResult> {

    private final JstRemoteService remoteService;
    private final JstOrderMapper jstOrderMapper;

    public JstOrderSubTask(Long subTaskId, JstOrderRootTask rootTask, String startSyncOffset, String endSyncOffset, JstRemoteService remoteService, JstOrderMapper jstOrderMapper) {
        super(subTaskId, rootTask, startSyncOffset, endSyncOffset);
        this.remoteService = remoteService;
        this.jstOrderMapper = jstOrderMapper;
    }

    @Override
    protected Source<FetchResult<OrdersSingleQuery, JstOrdersResult>, ?> fetch(SimpleJobContext context) {
        OrdersSingleQuery first = buildFirstRequest(context);
        return Source.unfoldAsync(first, key -> {
            if (key == null) {
                return CompletableFuture.completedFuture(Optional.empty());
            }
            JstOrdersResult jstOrdersResult = requestRemote(context, key);
            FetchResult<OrdersSingleQuery, JstOrdersResult> fetchResult = new FetchResult<>(key, jstOrdersResult);
            if (jstOrdersResult.isHasNext()) {
                OrdersSingleQuery next = BeanUtil.copyProperties(key, OrdersSingleQuery.class);
                next.setPageIndex(key.getPageIndex() + 1);
                return CompletableFuture.completedFuture(Optional.of(Pair.create(next, fetchResult)));
            }
            return CompletableFuture.completedFuture(Optional.of(Pair.create(null, fetchResult)));
        });
    }

    @Override
    protected OrdersSingleQuery buildFirstRequest(SimpleJobContext context) {
        OrdersSingleQuery query = new OrdersSingleQuery();
        query.setModifiedBegin(getStartSyncOffset());
        query.setModifiedEnd(getEndSyncOffset());
        query.setPageIndex(1);
        query.setPageSize(50);
        return query;
    }

    @Override
    protected JstOrdersResult requestRemote(SimpleJobContext context, OrdersSingleQuery request) {
        JstNewResult<JstOrdersResult> jstNewResult = remoteService.getOrders(getRootTask().getJstAuth(), request);
        if (jstNewResult.isSuccess()) {
            JstOrdersResult jstResult = jstNewResult.getData();
            JstResultWrapper jstResultWrapper = JstUtil.convertResult(jstResult, OrdersSingleDO::getOId);
            log.debug("请求聚水潭接口返回结果, {}, request: {}, result: {}",
                    context.getGroup(), JacksonUtil.toJsonString(request), JacksonUtil.toJsonString(jstResultWrapper));
            return jstResult;
        }
        log.error("请求聚水潭接口失败! method: {}, code: {}, msg: {}, query: {}",
                context.getGroup(), jstNewResult.getCode(), jstNewResult.getMsg(), JacksonUtil.toJsonString(request));
        throw new SliewException(Objects.toString(jstNewResult.getCode()), jstNewResult.getMsg());
    }

    @Override
    protected void persistData(SimpleJobContext context, OrdersSingleQuery request, JstOrdersResult response) {

    }
}
