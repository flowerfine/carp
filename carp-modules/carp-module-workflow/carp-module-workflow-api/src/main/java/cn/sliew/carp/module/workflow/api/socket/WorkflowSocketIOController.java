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

import cn.sliew.carp.framework.socketio.annotation.CarpSocketIoNamespace;
import cn.sliew.carp.framework.socketio.listener.CarpConnectionListener;
import cn.sliew.carp.framework.socketio.repository.SocketIORepository;
import cn.sliew.milky.common.util.JacksonUtil;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CarpSocketIoNamespace("/workflow")
public class WorkflowSocketIOController implements CarpConnectionListener {

    @Getter
    @Setter
    private SocketIONamespace namespace;
    @Getter
    @Setter
    private SocketIORepository repository;

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

    @OnEvent("customEvent")
    public void onCustomEvent(SocketIOClient client, AckRequest request, Object data) {
        log.info("receive custom event: {}, authData: {}", JacksonUtil.toJsonString(data), JacksonUtil.toJsonString(client.getHandshakeData().getAuthToken()));
        request.sendAckData("ok");
        sendBroadcastMessage(getUserId(client), "info", "info data");
    }
}
