package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.AddressCoordinatesDto;
import buildrun.roadeye.rest.dto.MessageWebSocketDto;
import buildrun.roadeye.rest.dto.OutputMessageWebSocket;
import buildrun.roadeye.service.CoordinatesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@MessageMapping("/location")
@RequiredArgsConstructor
public class DriverController {
    @MessageMapping("/public")
    @SendTo("/student/location/public")
    public OutputMessageWebSocket handleDriverLocation(MessageWebSocketDto webSocketDto) {
        return new OutputMessageWebSocket(webSocketDto.getLatitude(), webSocketDto.getLongitude(), LocalDateTime.now());
    }
}
