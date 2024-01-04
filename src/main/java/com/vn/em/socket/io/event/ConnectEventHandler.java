package com.vn.em.socket.io.event;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.vn.em.constant.CommonConstant;
import com.vn.em.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectEventHandler {

    private final JwtTokenProvider tokenProvider;

    @OnConnect
    private void onConnect(SocketIOClient client) {
        HandshakeData handshakeData = client.getHandshakeData();
        String accessToken = handshakeData.getSingleUrlParam(CommonConstant.Key.ACCESS_TOKEN);
        String userId = tokenProvider.extractSubjectFromJwt(accessToken);
        client.set(CommonConstant.Key.USER_ID, userId);
        client.set(CommonConstant.Key.ACCESS_TOKEN, accessToken);
        client.joinRoom(userId);
        log.info("Client user with id {} connected!", userId);
    }

}
