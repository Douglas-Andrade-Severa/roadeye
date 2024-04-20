package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.enums.service.RouteNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/routeNumber", produces = {"application/json"})
@Tag(name = "Route Number")
@SecurityRequirement(name = "bearer-key")
public class RouteNumberController {
    private final RouteNumberService routeNumberService;

    public RouteNumberController(RouteNumberService routeNumberService) {
        this.routeNumberService = routeNumberService;
    }

    @GetMapping
    @Operation(summary = "Get full route number", description = "Search all registered Routes numbers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route number found successfully"),
    })
    public ResponseEntity<?> ListOfAllRoutesNumbers() {
        return ResponseEntity.ok(routeNumberService.getAllRoutesNumbers());
    }
}
