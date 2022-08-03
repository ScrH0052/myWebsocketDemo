package com.example.mywebsocketdemo.controller;

import com.example.mywebsocketdemo.handler.MyWebSocketHandler;
import com.example.mywebsocketdemo.manager.MyWebSocketSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

@Controller
public class WebsocketController {
    @Resource
    private MyWebSocketHandler myWebSocketHandler;

    @GetMapping("/message/{token}/{message}")
    public void sendMessage(@PathVariable("token") String token, @PathVariable("message") String message) throws Exception {
        WebSocketSession session = MyWebSocketSessionManager.get(token);
        myWebSocketHandler.handleMessage(session, new TextMessage(message));
    }
}
