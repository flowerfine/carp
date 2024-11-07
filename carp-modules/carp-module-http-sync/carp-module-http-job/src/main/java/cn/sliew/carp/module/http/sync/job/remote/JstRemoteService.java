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

import cn.sliew.carp.module.http.sync.remote.jst.api.JstWmsClient;
import cn.sliew.carp.module.http.sync.remote.jst.request.base.ShopsQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.base.WmsPartnerQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.inventory.InventoryCountQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.inventory.InventoryQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.inventory.PackQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.item.*;
import cn.sliew.carp.module.http.sync.remote.jst.request.logistics.LogisticQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.order.OrderActionQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.order.OrdersOutSimpleQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.order.OrdersSingleQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.purchase.PurchaseQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.purchase.PurchaseinQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.purchase.PurchaseoutQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.purchase.SupplierQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.refund.RefundSingleQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.wms.AllocateQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.wms.InoutWaterQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.wms.OtherInoutQuery;
import cn.sliew.carp.module.http.sync.remote.jst.request.wms.TrackinfoQuery;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstNewResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.auth.JstAccessTokenDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.base.ShopDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.base.WmsPartnerDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.inventory.InventoryCountDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.inventory.JstInventoryResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.inventory.PackDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.item.*;
import cn.sliew.carp.module.http.sync.remote.jst.response.logistics.JstLogisticResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.JstOrdersResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.OrderActionDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.order.OrdersOutSimpleDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.PurchaseDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.PurchaseinDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.PurchaseoutDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.SupplierDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.refund.*;
import cn.sliew.milky.common.util.JacksonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Joiner;
import com.jushuitan.api.util.ApiUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class JstRemoteService implements InitializingBean {

    private URI uri;

    @Value("${jst.url}")
    private String url;

    @Autowired
    private JstWmsClient client;
    @Autowired
    private JstNewAuthorizationInfoMapper jstNewAuthorizationInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.uri = URI.create(url);
    }

    private boolean shouldRetry(Object tested) {
        if (tested == null) {
            return false;
        }
        if (tested instanceof JstNewResult == false) {
            return false;
        }
        JstNewResult jstNewResult = (JstNewResult) tested;
        switch (jstNewResult.getCode()) {
            case 199: // Exceed qps limit
            case 200: // Exceed qps limit
                return true;
            default:
                return false;
        }
    }

    private String sign(String appKey, String company, String param) {
        JstNewAuthorizationInfo authorizationInfo = selectAuthorizationInfo(appKey, company);
        Map<String, String> params = new HashMap();
        params.put("app_key", authorizationInfo.getAppKey());
        params.put("access_token", authorizationInfo.getAccessToken());
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("version", "2");
        params.put("charset", "utf-8");
        params.put("biz", param);
        String sign = ApiUtil.getSign(authorizationInfo.getAppSecret(), params);
        params.put("sign", sign);
        return format(params);
    }

    private String format(Map<String, String> params) {
        return Joiner.on("&").withKeyValueSeparator("=").join(params);
    }

    private JstNewAuthorizationInfo selectAuthorizationInfo(String appKey, String company) {
        LambdaQueryWrapper<JstNewAuthorizationInfo> queryWrapper = Wrappers.lambdaQuery(JstNewAuthorizationInfo.class)
                .eq(JstNewAuthorizationInfo::getAppKey, appKey)
                .eq(JstNewAuthorizationInfo::getCompany, company);
        return jstNewAuthorizationInfoMapper.selectOne(queryWrapper);
    }

    public JstNewResult<JstAccessTokenDO> accessToken(String appKeyParam, String appSecretParam) {
        Map<String, String> params = new HashMap();
        params.put("app_key", appKeyParam);
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("grant_type", "authorization_code");
        params.put("charset", "utf-8");
        params.put("code", RandomStringUtils.randomAlphabetic(6));
        String sign = ApiUtil.getSign(appSecretParam, params);
        params.put("sign", sign);
        return client.accessToken(uri, params);
    }

    public JstNewResult<JstAccessTokenDO> refreshToken(String appKeyParam, String appSecretParam, String refreshToken) {
        Map<String, String> params = new HashMap();
        params.put("app_key", appKeyParam);
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("grant_type", "refresh_token");
        params.put("charset", "utf-8");
        params.put("refresh_token", refreshToken);
        params.put("scope", "all");
        String sign = ApiUtil.getSign(appSecretParam, params);
        params.put("sign", sign);
        return client.refreshToken(uri, format(params));
    }

    public JstNewResult<JstResult<ShopDO>> getShops(String appKey, String company, ShopsQuery query) {
        return commonRetry.executeSupplier(() -> client.getShops(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstOrdersResult> getOrders(String appKey, String company, OrdersSingleQuery query) {
        return commonRetry.executeSupplier(() -> client.getOrders(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<RefundSingleDO>> getRefund(String appKey, String company, RefundSingleQuery query) {
        return commonRetry.executeSupplier(() -> client.getRefund(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<OrdersOutSimpleDO>> getOrdersOut(String appKey, String company, OrdersOutSimpleQuery query) {
        return commonRetry.executeSupplier(() -> client.getOrdersOut(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<AllocateDO>> getAllocate(String appKey, String company, AllocateQuery query) {
        return commonRetry.executeSupplier(() -> client.getAllocate(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<TrackinfoDO>> getTrackinfo(String appKey, String company, TrackinfoQuery query) {
        return commonRetry.executeSupplier(() -> client.getTrackinfo(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<OrderActionDO>> getOrderAction(String appKey, String company, OrderActionQuery query) {
        return longRetry.executeSupplier(() -> doGetOrderAction(appKey, company, query));
    }

    public JstNewResult<JstResult<OrderActionDO>> doGetOrderAction(String appKey, String company, OrderActionQuery query) {
        io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("order_action_query");
        return rateLimiter.executeSupplier(() -> client.getOrderAction(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<InoutWaterDO>> getInoutWater(String appKey, String company, InoutWaterQuery query) {
        return longRetry.executeSupplier(() -> doGetInoutWater(appKey, company, query));
    }

    public JstNewResult<JstResult<InoutWaterDO>> doGetInoutWater(String appKey, String company, InoutWaterQuery query) {
        io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("inout_water_query");
        return rateLimiter.executeSupplier(() -> client.getInoutWater(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<OtherInoutDO>> getOtherInout(String appKey, String company, OtherInoutQuery query) {
        return commonRetry.executeSupplier(() -> client.getOtherInout(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<WmsPartnerDO>> getWmsPartner(String appKey, String company, WmsPartnerQuery query) {
        return commonRetry.executeSupplier(() -> client.getWmsPartner(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<SkumapDO>> getSkumap(String appKey, String company, SkumapQuery query) {
        return commonRetry.executeSupplier(() -> doGetSkumap(appKey, company, query));
    }

    private JstNewResult<JstResult<SkumapDO>> doGetSkumap(String appKey, String company, SkumapQuery query) {
        io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("skumap_query");
        return rateLimiter.executeSupplier(() -> client.getSkumap(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<MallItemDO>> getMallItem(String appKey, String company, MallItemQuery query) {
        return commonRetry.executeSupplier(() -> client.getMallItem(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<CombineSkuDO>> getCombineSku(String appKey, String company, CombineSkuQuery query) {
        return commonRetry.executeSupplier(() -> client.getCombineSku(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<SkuDO>> getSku(String appKey, String company, SkuQuery query) {
        io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("sku_query");
        return rateLimiter.executeSupplier(() -> doGetSku(appKey, company, query));
    }

    public JstNewResult<JstResult<SkuDO>> doGetSku(String appKey, String company, SkuQuery query) {
        return commonRetry.executeSupplier(() -> client.getSku(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<PurchaseDO>> getPurchase(String appKey, String company, PurchaseQuery query) {
        return commonRetry.executeSupplier(() -> client.getPurchase(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<PurchaseinDO>> getPurchasein(String appKey, String company, PurchaseinQuery query) {
        return commonRetry.executeSupplier(() -> client.getPurchasein(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<PurchaseoutDO>> getPurchaseout(String appKey, String company, PurchaseoutQuery query) {
        return commonRetry.executeSupplier(() -> client.getPurchaseout(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<PackDO>> getPack(String appKey, String company, PackQuery query) {
        return commonRetry.executeSupplier(() -> client.getPack(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<SupplierDO>> getSupplier(String appKey, String company, SupplierQuery query) {
        return commonRetry.executeSupplier(() -> doGetSupplier(appKey, company, query));
    }

    public JstNewResult<JstResult<SupplierDO>> doGetSupplier(String appKey, String company, SupplierQuery query) {
        io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("supplier_query");
        return rateLimiter.executeSupplier(() -> client.getSupplier(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstLogisticResult> getLogisticInfo(String appKey, String company, LogisticQuery query) {
        return commonRetry.executeSupplier(() -> doGetLogisticInfo(appKey, company, query));
    }

    public JstNewResult<JstLogisticResult> doGetLogisticInfo(String appKey, String company, LogisticQuery query) {
        io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("logistic_info_query");
        return rateLimiter.executeSupplier(() -> client.getLogisticInfo(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<CategoryDO>> getCategoryInfo(String appKey, String company, CategoryQuery query) {
        return commonRetry.executeSupplier(() -> client.getCategoryInfo(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstInventoryResult> getInventoryQuery(String appKey, String company, InventoryQuery query) {
        return commonRetry.executeSupplier(() -> client.getInventoryQuery(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }

    public JstNewResult<JstResult<InventoryCountDO>> getInventoryCount(String appKey, String company, InventoryCountQuery query) {
        return commonRetry.executeSupplier(() -> client.getInventoryCount(uri, sign(appKey, company, JacksonUtil.toJsonString(query))));
    }
}
