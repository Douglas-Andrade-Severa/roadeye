package buildrun.roadeye.rest.dto.service.implementation;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class CustomStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Conexão estabelecida com o servidor WebSocket.");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Mensagem recebida:");
        System.out.println("Headers: " + headers);
        System.out.println("Payload: " + payload);
    }
}
