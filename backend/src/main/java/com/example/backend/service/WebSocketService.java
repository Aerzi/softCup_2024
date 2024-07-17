package com.example.backend.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface WebSocketService {
    public void connect(String serverUrl) throws InterruptedException, ExecutionException;
    public void sendMessage(String message) throws IOException;
    public void close() throws IOException;
    String getReceivedMessage();
}
