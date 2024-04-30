package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.RouteRequest;
import buildrun.roadeye.service.RouteService;
import buildrun.roadeye.service.implementation.GoogleMapsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/routes", produces = {"application/json"})
@Tag(name = "Route")
@SecurityRequirement(name = "bearer-key")
public class RouteController {
    private final GoogleMapsService googleMapsService;

    public RouteController(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @PostMapping
    public ResponseEntity<String> computeRoutes(@RequestBody RouteRequest request) {
        ResponseEntity<String> response = googleMapsService.computeRoutes(request);
        return response;
    }
}
