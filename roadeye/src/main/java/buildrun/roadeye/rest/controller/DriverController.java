package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.AddressCoordinatesDto;
import buildrun.roadeye.service.CoordinatesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Tag(name = "Driver location")
@SecurityRequirement(name = "bearer-key")
public class DriverController {
    private final CoordinatesService coordinatesService;

    public DriverController(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @MessageMapping("/driverCoordinates")
    public void sendCoordinates(AddressCoordinatesDto coordinates) {
        coordinatesService.processCoordinates(coordinates);
    }
}
