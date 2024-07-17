package com.example.backend.service.impl;

import com.example.backend.service.WebSocketService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    private WebSocketClient webSocketClient;
    private WebSocketSession session;
    private String receivedMessage;
    private CountDownLatch latch;

    @Override
    public void connect(String serverUrl) throws InterruptedException, ExecutionException {
        this.latch = new CountDownLatch(1);
        this.session = webSocketClient.doHandshake(new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("Connected to server");
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessage = message.getPayload();
                System.out.println("Received: " + message.getPayload());
                latch.countDown(); // 释放锁
            }
        }, serverUrl).get();
    }

    @Override
    public void sendMessage(String message) throws IOException {
        if (this.session != null && this.session.isOpen()) {
            this.session.sendMessage(new TextMessage(message));
        } else {
            System.out.println("WebSocket session is not open");
        }
    }
    @Override
    public void close() throws IOException {
        if (this.session != null) {
            this.session.close();
        }
    }
    @Override
    public String getReceivedMessage() {
        try {
            latch.await(); // 等待消息接收完成
            return receivedMessage;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
