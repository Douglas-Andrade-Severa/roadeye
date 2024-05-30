package buildrun.roadeye.rest.controller;

import buildrun.roadeye.service.CoordinatesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@Tag(name = "Student")
@SecurityRequirement(name = "bearer-key")
public class StudentWebSocketController {
    private final CoordinatesService coordinatesService;

    public StudentWebSocketController(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @MessageMapping("/student/subscribe")
    public void subscribeCoordinatesUpdates(UUID studentId) {
        coordinatesService.subscribeCoordinatesUpdates(studentId);
    }
}
