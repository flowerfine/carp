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
package cn.sliew.carp.module.workflow.api.socket;

import cn.sliew.carp.framework.log.realtime.configuration.RealtimeLogPollProperties;
import cn.sliew.carp.framework.log.realtime.poll.StreamPollerImpl;
import cn.sliew.carp.framework.log.realtime.poll.redis.RedisStreamIterator;
import cn.sliew.carp.framework.socketio.annotation.CarpSocketIoNamespace;
import cn.sliew.carp.framework.socketio.listener.CarpConnectionListener;
import cn.sliew.carp.framework.socketio.repository.SocketIORepository;
import cn.sliew.carp.module.workflow.api.socket.param.ReadEventsParam;
import cn.sliew.carp.module.workflow.api.socket.param.ReadLogsParam;
import cn.sliew.carp.module.workflow.api.util.WorkflowUtil;
import cn.sliew.milky.common.util.JacksonUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@CarpSocketIoNamespace(namespace = "/workflow")
public class WorkflowSocketIOController implements CarpConnectionListener, InitializingBean, DisposableBean {

    @Getter
    @Setter
    private SocketIONamespace namespace;
    @Getter
    @Setter
    private SocketIORepository repository;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RealtimeLogPollProperties properties;

    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setQueueCapacity(32);
        taskExecutor.afterPropertiesSet();
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(taskExecutor)) {
            taskExecutor.destroy();
        }
    }

    @OnConnect
    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        connect(socketIOClient);
        HandshakeData handshakeData = socketIOClient.getHandshakeData();
        log.info("Socket.IO connected, sessionId: {}, authData: {}",
                socketIOClient.getSessionId(), JacksonUtil.toJsonString(handshakeData.getAuthToken()));
    }

    @OnDisconnect
    @Override
    public void onDisConnect(SocketIOClient socketIOClient) {
        disconnect(socketIOClient);
        log.warn("socket.io disconnected, sessionId: {}", socketIOClient.getSessionId());
    }

    @OnEvent("readLogs")
    public void onReadLogs(SocketIOClient client, AckRequest request, ReadLogsParam param) {
        request.sendAckData("ok");
        String streamKey = WorkflowUtil.buildWorkflowInstanceLogsKey(param.getWorkflowInstanceId());
        CompletableFuture.runAsync(() -> {
            RedisStreamIterator iterator = new RedisStreamIterator(redisTemplate, String.class, streamKey);
            try (StreamPollerImpl streamPoller = new StreamPollerImpl(iterator, taskExecutor, properties)) {
                while (true) {
                    List<String> datas = streamPoller.poll(10, Duration.ofSeconds(1L));
                    if (CollectionUtils.isEmpty(datas) == false) {
                        for (String data : datas) {
                            sendBroadcastMessage(getUserId(client), "pushLogs", data);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Poll workflow logs error, streamKey: {}", streamKey, e);
            }
        });
    }

    @OnEvent("readEvents")
    public void onReadEvents(SocketIOClient client, AckRequest request, ReadEventsParam param) {
        request.sendAckData("ok");
        String streamKey = WorkflowUtil.buildWorkflowInstanceEventsKey(param.getWorkflowInstanceId());
        CompletableFuture.runAsync(() -> {
            RedisStreamIterator iterator = new RedisStreamIterator(redisTemplate, String.class, streamKey);
            try (StreamPollerImpl streamPoller = new StreamPollerImpl(iterator, taskExecutor, properties)) {
                while (true) {
                    List<String> datas = streamPoller.poll(10, Duration.ofSeconds(1L));
                    if (CollectionUtils.isEmpty(datas) == false) {
                        for (String data : datas) {
                            sendBroadcastMessage(getUserId(client), "pushEvents", data);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Poll workflow events error, streamKey: {}", streamKey, e);
            }
        });
    }

    @OnEvent("customEvent")
    public void onCustomEvent(SocketIOClient client, AckRequest request, Object data) {
        log.info("receive custom event: {}, authData: {}", JacksonUtil.toJsonString(data), JacksonUtil.toJsonString(client.getHandshakeData().getAuthToken()));
        request.sendAckData("ok");
        sendBroadcastMessage(getUserId(client), "info", "info data");
    }
}
