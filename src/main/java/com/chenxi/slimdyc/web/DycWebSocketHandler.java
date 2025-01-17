package com.chenxi.slimdyc.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.chenxi.slimdyc.core.DynamicThreadPoolManager;
import com.chenxi.slimdyc.domain.ThreadPoolStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

/**
 * 具体的 WebSocket 逻辑
 *
 * @author chenxi
 * @date 2025/1/13 12:07
 */
public class DycWebSocketHandler extends TextWebSocketHandler {
    private final WebSocketSessionManager sessionManager;
    private final DynamicThreadPoolManager dynamicThreadPoolManager;

    public DycWebSocketHandler(WebSocketSessionManager sessionManager, DynamicThreadPoolManager dynamicThreadPoolManager) {
        this.sessionManager = sessionManager;
        this.dynamicThreadPoolManager = dynamicThreadPoolManager;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessionManager.addSession(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        JSONObject data = JSON.parseObject(payload);
        String type = data.getString("type");

        if ("updatePoolStatus".equals(type)) {
            JSONObject status = data.getJSONObject("status");
            String poolName = status.getString("poolName");
            int corePoolSize = status.getIntValue("corePoolSize");
            int maxPoolSize = status.getIntValue("maximumPoolSize");

            // 更新线程池逻辑
            dynamicThreadPoolManager.updateStatus(poolName, corePoolSize, maxPoolSize);
        }

        if ("nowPoolStatus".equals(type)) {
            List<ThreadPoolStatus> threadPoolStatusList = dynamicThreadPoolManager.getAllPoolsStatus();
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(JSON.toJSONString(threadPoolStatusList)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        sessionManager.removeSession(session.getId());
    }
}
