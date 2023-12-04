package com.vn.em.socket.io.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.vn.em.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DisconnectEventHandler {

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        for (String room : client.getAllRooms()) {
            client.leaveRoom(room);
        }
        String userId = client.get(CommonConstant.Key.USER_ID);
        log.info("Client user with id {} disconnected", userId);
    }

}