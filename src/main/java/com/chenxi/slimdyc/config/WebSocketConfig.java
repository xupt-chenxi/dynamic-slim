package com.chenxi.slimdyc.config;

import com.chenxi.slimdyc.core.DynamicThreadPoolManager;
import com.chenxi.slimdyc.web.DycWebSocketHandler;
import com.chenxi.slimdyc.web.WebSocketSessionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * WebSocket 配置类
 *
 * @author chenxi
 * @date 2025/1/13 11:56
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketSessionManager sessionManager;
    private final DynamicThreadPoolManager dynamicThreadPoolManager;

    public WebSocketConfig(WebSocketSessionManager sessionManager, DynamicThreadPoolManager dynamicThreadPoolManager) {
        this.sessionManager = sessionManager;
        this.dynamicThreadPoolManager = dynamicThreadPoolManager;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new DycWebSocketHandler(sessionManager, dynamicThreadPoolManager), "/poolStatus")
                .setAllowedOrigins("*");  // 允许所有的来源
    }
}
