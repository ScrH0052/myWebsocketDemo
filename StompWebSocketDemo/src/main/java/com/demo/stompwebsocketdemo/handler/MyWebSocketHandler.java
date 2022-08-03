package com.demo.stompwebsocketdemo.handler;

import com.demo.stompwebsocketdemo.manager.MyWebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
@Slf4j
public class MyWebSocketHandler implements WebSocketHandler {


    /**
     * 建立连接
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 缓存用户信息
        log.info("建立连接成功，用户name:{}",session.getId());
        String name = session.getPrincipal().getName();
        MyWebSocketSessionManager.add(name,session);
        log.info("name: {}, 建立连接成功", name);

    }

    /**
     * 接收用户信息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Object payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        log.info("server 接收到 " + token + " 发送的 " + payload);
        session.sendMessage(message);
        log.info("消息发送完毕");
    }

    /**
     * 处理错误
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String errMsg = "Error! " + exception.getMessage();
        TextMessage errorMessage = new TextMessage(errMsg);
        session.sendMessage(errorMessage);

    }

    /**
     * 关闭连接
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户退出，移除缓存
            MyWebSocketSessionManager.remove(token.toString());
        }
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
