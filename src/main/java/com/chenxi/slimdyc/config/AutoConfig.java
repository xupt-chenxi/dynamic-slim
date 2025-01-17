package com.chenxi.slimdyc.config;

import com.chenxi.slimdyc.core.DynamicThreadPoolManager;
import com.chenxi.slimdyc.web.ThreadPoolStatusBroadcaster;
import com.chenxi.slimdyc.web.WebSocketSessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于实现自动装配
 *
 * @author chenxi
 * @date 2025/1/17 16:33
 */
@Configuration
public class AutoConfig {
    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManager();
    }

    @Bean
    public DynamicThreadPoolManager dynamicThreadPoolManager(ApplicationContext applicationContext) {
        return new DynamicThreadPoolManager(applicationContext);
    }

    @Bean
    public ThreadPoolStatusBroadcaster threadPoolStatusBroadcaster(WebSocketSessionManager sessionManager, DynamicThreadPoolManager dynamicThreadPoolManager) {
        return new ThreadPoolStatusBroadcaster(sessionManager, dynamicThreadPoolManager);
    }
}
