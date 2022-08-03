package com.example.mywebsocketdemo.config;

import com.example.mywebsocketdemo.constant.WebSocketConstant;
import com.example.mywebsocketdemo.handler.MyWebSocketHandler;
import com.example.mywebsocketdemo.interceptor.MyWebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * `@EnableWebSocket` 开启websocket功能
 * @author humy
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private MyWebSocketHandler myWebSocketHandler;
    @Resource
    private MyWebSocketInterceptor myWebSocketInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /*
          addHandler 添加处理器
          addInterceptors 添加拦截器
          setAllowedOrigins 解决跨域问题 * 代表允许所有请求资源
         */
        registry.addHandler(myWebSocketHandler, WebSocketConstant.WEB_SOCKET_HANDLER)
                .addInterceptors(myWebSocketInterceptor)
                .setAllowedOrigins("*");
    }


}
