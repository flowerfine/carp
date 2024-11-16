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

package cn.sliew.carp.module.http.sync.job.task.jst.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.sliew.carp.framework.exception.SliewException;
import cn.sliew.carp.module.http.sync.framework.model.FetchResult;
import cn.sliew.carp.module.http.sync.framework.model.internal.SimpleJobContext;
import cn.sliew.carp.module.http.sync.job.remote.JstRemoteService;
import cn.sliew.carp.module.http.sync.job.repository.entity.jst.JstOrder;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstOrderMapper;
import cn.sliew.carp.module.http.sync.job.task.jst.AbstractJstSubTask;
import cn.sliew.carp.module.http.sync.job.task.jst.util.JstResultWrapper;
import cn.sliew.carp.module.http.sync.job.task.jst.util.JstUtil;
import cn.sliew.carp.module.http.sync.remote.jst.request.order.OrdersSingleQuery;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstNewResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.JstOrdersResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.OrdersSingleDO;
import cn.sliew.milky.common.util.JacksonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
        if (CollectionUtils.isEmpty(response.getDatas())) {
            return;
        }
        response.getDatas().forEach(data -> upsert(request, data));
    }

    private void upsert(OrdersSingleQuery request, OrdersSingleDO data) {
        JstOrder record = convert(request, data);

        LambdaQueryWrapper<JstOrder> queryWrapper = Wrappers.lambdaQuery(JstOrder.class)
                .eq(JstOrder::getAppKey, record.getAppKey())
                .eq(JstOrder::getCompany, record.getCompany())
                .eq(JstOrder::getOId, record.getOId())
                .select(JstOrder::getId);

        JstOrder jstOrder = jstOrderMapper.selectOne(queryWrapper);
        if (jstOrder != null) {
            record.setId(jstOrder.getId());
            record.setEditor("sync-task");
            jstOrderMapper.updateById(record);
        } else {
            record.setCreator("sync-task");
            record.setEditor("sync-task");
            jstOrderMapper.insert(record);
        }
    }

    private JstOrder convert(OrdersSingleQuery request, OrdersSingleDO data) {
        JstOrder record = BeanUtil.copyProperties(data, JstOrder.class);
        if (CollectionUtils.isEmpty(data.getItems()) == false) {
            record.setItems(JacksonUtil.toJsonString(data.getItems()));
        }
        if (org.springframework.util.CollectionUtils.isEmpty(data.getPays()) == false) {
            record.setPays(JacksonUtil.toJsonString(data.getPays()));
        }
        if (org.springframework.util.CollectionUtils.isEmpty(data.getRawSoIds()) == false) {
            record.setRawSoIds(JacksonUtil.toJsonString(data.getRawSoIds()));
        }
        record.setSyncStartTime(DateUtil.parse(request.getModifiedBegin(), DatePattern.NORM_DATETIME_PATTERN).toJdkDate());
        record.setSyncEndTime(DateUtil.parse(request.getModifiedEnd(), DatePattern.NORM_DATETIME_PATTERN).toJdkDate());
        record.setSyncPageIndex(request.getPageIndex());
        record.setSyncPageSize(request.getPageSize());
        return record;
    }
}
