package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Vehicle;
import buildrun.roadeye.rest.dto.VehicleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleService {
    VehicleDto createVehicle(VehicleDto vehicleDto);

    ResponseEntity<?> getAllVehicle();

    ResponseEntity<?> deleteVehicle(Long vehicleId);

    ResponseEntity<?> updateVehicle(Long vehicleId, VehicleDto updateVehicleDto);

    ResponseEntity<?> getVehicleById(Long vehicleId);
}
