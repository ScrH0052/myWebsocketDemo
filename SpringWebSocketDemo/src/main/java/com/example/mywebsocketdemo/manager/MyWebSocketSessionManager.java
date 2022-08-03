package com.example.mywebsocketdemo.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyWebSocketSessionManager {

    /**
     * 保存 webSocket 连接
     */
    private static Map<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加
     * @param key
     * @param session
     */
    public static void add(String key, WebSocketSession session) {
        SESSION_POOL.put(key, session);
    }

    /**
     * 删除session
     *
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        return SESSION_POOL.remove(key);
    }

    /**
     * 删除并关闭连接
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        try {
            session.close();
        } catch (IOException e) {
            log.error("关闭连接失败，errMsg:{}", e.getMessage());
        }
    }

    /**
     * 获得连接
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        return SESSION_POOL.get(key);
    }

}
