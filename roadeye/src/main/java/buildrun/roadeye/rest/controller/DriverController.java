package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.AddressCoordinatesDto;
import buildrun.roadeye.rest.dto.MessageWebSocketDto;
import buildrun.roadeye.rest.dto.OutputMessageWebSocket;
import buildrun.roadeye.service.CoordinatesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@Tag(name = "Driver location")
@SecurityRequirement(name = "bearer-key")
public class DriverController {
    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    @MessageMapping("/location")
    @SendTo("/topic/messages")
    public OutputMessageWebSocket handleDriverLocation(MessageWebSocketDto webSocketDto) {
        logger.info("Received WebSocket message: latitude={}, longitude={}", webSocketDto.getLatitude(), webSocketDto.getLongitude());
        return new OutputMessageWebSocket(webSocketDto.getLatitude(), webSocketDto.getLongitude(), LocalDateTime.now());
    }
}
