package buildrun.roadeye.service.implementation;

import buildrun.roadeye.rest.dto.OutputMessageWebSocket;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class CustomStompFrameHandler implements StompFrameHandler {
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return OutputMessageWebSocket.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        OutputMessageWebSocket msg = (OutputMessageWebSocket) payload;

    }
}