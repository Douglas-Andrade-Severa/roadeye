package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.service.VehicleService;
import buildrun.roadeye.domain.repository.VehicleRespository;
import buildrun.roadeye.rest.dto.VehicleDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImplementation implements VehicleService {
    private static final String axNotFound = "Vehicle not found";
    private final VehicleRespository vehicleRespository;

    public VehicleServiceImplementation(VehicleRespository vehicleRespository) {
        this.vehicleRespository = vehicleRespository;
    }

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        try {
            var vehivleFromLicensePlate = vehicleRespository.findByLicensePlate(vehicleDto.licensePlate());
            if (vehivleFromLicensePlate.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Vehicle already exists.");
            }
            var vehivleFromNumberRenavan = vehicleRespository.findByNumberRenavan(vehicleDto.numberRenavam());
            if (vehivleFromNumberRenavan.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Vehicle already exists.");
            }
            Vehicle vehicle = new Vehicle();
            setVehicleService(vehicle, vehicleDto);
            Vehicle newVehicle = vehicleRespository.save(vehicle);
            return vehicleDto.fromEntity(newVehicle);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }
    @Override
    public ResponseEntity<?> getAllVehicle() {
        List<Vehicle> vehicles = vehicleRespository.findAll();
        if (!vehicles.isEmpty()) {
            return ResponseEntity.ok(vehicles);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No vehicles found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> deleteVehicle(Long vehicleId) {
        Optional<Vehicle> optionalvehicle = vehicleRespository.findById(vehicleId);
        if (optionalvehicle.isPresent()) {
            vehicleRespository.deleteById(vehicleId);
            ErrorResponse errorResponse = new ErrorResponse("Vehicle deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Vehicle does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateVehicle(Long vehicleId, VehicleDto updateVehicleDto) {
        Optional<Vehicle> optionalvehicle = vehicleRespository.findById(vehicleId);
        if (optionalvehicle.isPresent()) {
            Vehicle vehicle = optionalvehicle.get();
            setVehicleService(vehicle, updateVehicleDto);
            return ResponseEntity.ok(vehicleRespository.save(vehicle));
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Vehicle does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRespository.findById(vehicleId).orElse(null);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Vehicle does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Transactional
    private void setVehicleService(Vehicle vehicle, VehicleDto vehicleDto) {
        vehicle.setBrand(vehicleDto.brand());
        vehicle.setModel(vehicleDto.model());
        vehicle.setLicensePlate(vehicleDto.licensePlate());
        vehicle.setNumberRenavan(vehicleDto.numberRenavam());
        vehicle.setYearManufacturing(vehicleDto.yearManufacturing());
        vehicle.setTypeVehicleEnum(vehicleDto.typeVehicleEnum());
        vehicle.setColorEnum(vehicleDto.colorEnum());
        vehicle.setStatusEnum(vehicleDto.statusEnum());
    }
}
