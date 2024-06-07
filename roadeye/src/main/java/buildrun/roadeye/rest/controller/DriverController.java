package buildrun.roadeye.rest.controller;

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
import java.util.UUID;

@Controller
@Tag(name = "Driver location")
@SecurityRequirement(name = "bearer-key")
public class DriverController {
    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/location")
//    @SendTo("/topic/response")
//    public OutputMessageWebSocket handleDriverLocation(@Payload MessageWebSocketDto webSocketDto) {
//        // Repassar a informação sem alterações
//        OutputMessageWebSocket outputMessage = new OutputMessageWebSocket(webSocketDto.getLatitude(), webSocketDto.getLongitude(), LocalDateTime.now());
//
//        // Log indicando que a informação foi recebida e repassada
//        logger.info("Received location update - Latitude: {}, Longitude: {}", webSocketDto.getLatitude(), webSocketDto.getLongitude());
//
//        return outputMessage;
//    }
    public void handleMessage(@Payload String message) {
        // Processar a mensagem recebida...

        // Enviar uma resposta de volta para o cliente
        String resposta = "Resposta à mensagem: " + message;
        messagingTemplate.convertAndSend("/topic/response", resposta);
    }
}
