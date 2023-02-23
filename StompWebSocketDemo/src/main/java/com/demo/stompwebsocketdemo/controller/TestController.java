package com.demo.stompwebsocketdemo.controller;

import com.demo.stompwebsocketdemo.entity.Chat;
import com.demo.stompwebsocketdemo.handler.MyWebSocketHandler;
import com.demo.stompwebsocketdemo.manager.MyWebSocketSessionManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TestController {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/onlineChat")
    public void chat(Principal principal, Chat chat) {
        chat.setFrom(principal.getName());
        chat.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chat.getTo(),"/testChannel/aaa",chat);
    }
    @MessageMapping("/chat2")
    @SendTo("/queue/chat")
    public Chat chat2(Principal principal, Chat chat) {
        chat.setFrom(principal.getName());
        chat.setDate(new Date());
        // destination中，第一个必须为config中配置的一个enableSimpleBroker地址，否则无法生效
        simpMessagingTemplate.convertAndSendToUser(chat.getTo(),"/testChannel/aaa",chat.getContent());
        return chat;
    }

    @Resource
    private MyWebSocketHandler myWebSocketHandler;

    @GetMapping("/message/{token}/{message}")
    public void sendMessage(@PathVariable("token") String token, @PathVariable("message") String message) throws Exception {
        WebSocketSession session = MyWebSocketSessionManager.get(token);
        myWebSocketHandler.handleMessage(session, new TextMessage(message));
    }

    @GetMapping("/test")
    @ResponseBody
    public void testSendMessage() {
        simpMessagingTemplate.convertAndSendToUser("admin", "/testChannel",
                new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
    }
}
