package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.Message;
import buildrun.roadeye.rest.dto.MessageWebSocketDto;
import buildrun.roadeye.rest.dto.OutputMessageWebSocket;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@Tag(name = "Websocket controller")
@SecurityRequirement(name = "bearer-key")
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/location")
    @SendTo("/topic/location")
    public OutputMessageWebSocket handleDriverLocation(@Payload MessageWebSocketDto webSocketDto) {
        // Repassar a informação sem alterações
        OutputMessageWebSocket outputMessage = new OutputMessageWebSocket(webSocketDto.getLatitude(), webSocketDto.getLongitude(), LocalDateTime.now());
        // Log indicando que a informação foi recebida e repassada
        logger.info("Received location update - Latitude: {}, Longitude: {}", webSocketDto.getLatitude(), webSocketDto.getLongitude());
        return outputMessage;
    }

    //Não implementado, só uma brincadeira
    @MessageMapping("/chatMessage")
    @SendTo("/chat")
    public Message sendMessage(Message message){
        return message;
    }
}
