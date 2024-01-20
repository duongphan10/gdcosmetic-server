package com.vn.em.socket.io;

import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.vn.em.constant.CommonConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.exception.UnauthorizedException;
import com.vn.em.security.jwt.JwtTokenProvider;
import com.vn.em.socket.io.exception.SocketIOEventException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@org.springframework.context.annotation.Configuration
public class SocketIOConfig {
    @Value("${socket.io.host}")
    private String host;
    @Value("${socket.io.port}")
    private Integer port;

    private final SocketIOEventException socketIOEventException;

    private final JwtTokenProvider tokenProvider;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        //config.setHostname(host);
        config.setPort(port);
        config.setOrigin("*");
        config.setAuthorizationListener(handshakeData -> {
            String accessToken = handshakeData.getSingleUrlParam(CommonConstant.Key.ACCESS_TOKEN);
            if (!StringUtils.hasText(accessToken) || !tokenProvider.validateToken(accessToken)) {
                throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
            }
            return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
        });
        config.setExceptionListener(socketIOEventException);
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
