package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.Route;
import buildrun.roadeye.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/route", produces = {"application/json"})
@Tag(name = "Route")
@SecurityRequirement(name = "bearer-key")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    @Operation(summary = "Get full route", description = "Search all registered Routes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route found successfully"),
    })
    public ResponseEntity<List<Route>> ListOfAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }
}
