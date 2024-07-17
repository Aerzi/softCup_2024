package com.example.backend.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class WebSocketTest extends WebSocketClient {

    private static final Gson gson = new Gson();

    private ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketTest(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");

        // 发送有效的 JSON 格式消息
        Message message = new Message();
        message.setQuestion("基于uml的旅游团队管理系统的开发");
        message.setMethod_choice("1");
        this.send(gson.toJson(message));
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        Result result = null;
        try {
            result = objectMapper.readValue(message, Result.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.printResult();


    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from server with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("An error occurred:" + ex);
    }

    public static void main(String[] args) {
        try {
            WebSocketTest client = new WebSocketTest(new URI("ws://localhost:8890"));
            client.connect();

            // Wait for the connection to establish
            while (!client.isOpen()) {
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    static class Message {
        private String question;
        private String method_choice;
    }

    @Getter
    @Setter
    static class Result {
        private List<String> result;

        public void printResult() {
            int i = 0;
            for (String s : result) {
                System.out.println(s + i);
                i++;
            }
        }
    }

}
