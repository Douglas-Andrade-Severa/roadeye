package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.VehicleDto;
import buildrun.roadeye.domain.enums.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/vehicle", produces = {"application/json"})
@Tag(name = "Vehicle")
@SecurityRequirement(name = "bearer-key")
public class VehicleController {
    private final VehicleService vehicleService;
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    @PostMapping
    @Operation(summary = "Create vehicle", description = "Insert vehicle.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle create"),
    })
    private VehicleDto createVehicle(@RequestBody VehicleDto vehicleDto){
        return vehicleService.createVehicle(vehicleDto);
    }

    @GetMapping
    @Operation(summary = "Get full vehicle", description = "Search all registered vehicle", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "vehicle found successfully"),
    })
    public ResponseEntity<?>  listVehicle() {
        return ResponseEntity.ok(vehicleService.getAllVehicle());
    }

    @DeleteMapping("/{vehicleId}")
    @Operation(summary = "Delete vehicle by ID", description = "deleted vehicle will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "vehicle deleted successfully"),
    })
    public ResponseEntity<?> deleteVehicle(@PathVariable Long vehicleId) {
        return vehicleService.deleteVehicle(vehicleId);
    }

    @PutMapping("/{vehicleId}")
    @Operation(summary = "Update vehicle by ID", description = "The vehicle will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "user updated successfully"),
    })
    public ResponseEntity<?> updateVehicle(@PathVariable Long vehicleId, @Validated @RequestBody VehicleDto updateVehicleDto) {
        return vehicleService.updateVehicle(vehicleId, updateVehicleDto);
    }

    @GetMapping("/{vehicleId}")
    @Operation(summary = "Get vehicle by ID", description = "Retrieve vehicle information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ok"),
    })
    public ResponseEntity<?> getUserById(@PathVariable Long vehicleId) {
        return vehicleService.getVehicleById(vehicleId);
    }
}
