package com.chenxi.slimdyc.web;

import com.alibaba.fastjson2.JSON;
import com.chenxi.slimdyc.core.DynamicThreadPoolManager;
import com.chenxi.slimdyc.domain.ThreadPoolStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * 定时向前端推送线程池信息
 *
 * @author chenxi
 * @date 2025/1/13 12:04
 */
public class ThreadPoolStatusBroadcaster {
    private final WebSocketSessionManager sessionManager;
    private final DynamicThreadPoolManager dynamicThreadPoolManager;

    public ThreadPoolStatusBroadcaster(WebSocketSessionManager sessionManager, DynamicThreadPoolManager dynamicThreadPoolManager) {
        this.sessionManager = sessionManager;
        this.dynamicThreadPoolManager = dynamicThreadPoolManager;
    }

    @Scheduled(fixedRate = 10000)  // 每 10 秒推送一次
    public void broadcastThreadPoolStatus() {
        List<ThreadPoolStatus> threadPoolStatusList = dynamicThreadPoolManager.getAllPoolsStatus();
        for (WebSocketSession session : sessionManager.getSessions().values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(JSON.toJSONString(threadPoolStatusList)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
