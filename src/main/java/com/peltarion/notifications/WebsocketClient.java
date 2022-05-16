package com.peltarion.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;

@Service
//connects and sends to a websocket
public class WebsocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketClient.class);

    private WebSocketSession webSocketSession;

    private void connect() {
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();

            webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                }
            }, new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/chat")).get();

        } catch (Exception e) {
            LOGGER.error("Exception while accessing websockets", e);
        }
    }

    public void sendMessage(String message) {
        connect();
        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            LOGGER.error("Failed to send message: {}", message, e);
        }
    }
}
