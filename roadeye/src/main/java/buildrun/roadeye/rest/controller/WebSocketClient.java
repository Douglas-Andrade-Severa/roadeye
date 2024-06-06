package buildrun.roadeye.rest.controller;

import buildrun.roadeye.service.implementation.CustomStompSessionHandler;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Component
public class WebSocketClient {
    private final WebSocketStompClient stompClient;

    public WebSocketClient(WebSocketStompClient stompClient) {
        this.stompClient = stompClient;
    }

    public void connectAndSubscribe(String loggerServerQueueUrl, String principalRequestHeader, String principalRequestValue) throws ExecutionException, InterruptedException {
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        handshakeHeaders.add(principalRequestHeader, principalRequestValue);

        StompSessionHandler sessionHandler = new CustomStompSessionHandler();
        StompSession stompSession = stompClient.connect(loggerServerQueueUrl, handshakeHeaders, sessionHandler).get();
        // Restante do código para enviar mensagens, inscrever-se para receber mensagens, etc.
    }
}
