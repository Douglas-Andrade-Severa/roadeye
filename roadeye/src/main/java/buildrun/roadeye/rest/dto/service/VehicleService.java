package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Vehicle;
import buildrun.roadeye.rest.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    VehicleDto createVehicle(VehicleDto vehicleDto);

    List<Vehicle> getAllVehicle();

    void deleteVehicle(Long vehicleId);

    Vehicle updateVehicle(Long vehicleId, VehicleDto updateVehicleDto);

    Vehicle getVehicleById(Long vehicleId);
}
