package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.Vehicle;
import buildrun.roadeye.domain.repository.VehicleRespository;
import buildrun.roadeye.rest.dto.VehicleDto;
import buildrun.roadeye.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
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
    public List<Vehicle> getAllVehicle() {
        return vehicleRespository.findAll();
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRespository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException(axNotFound));
        vehicleRespository.delete(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long vehicleId, VehicleDto updateVehicleDto) {
        Vehicle vehicle = vehicleRespository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException(axNotFound));
        setVehicleService(vehicle, updateVehicleDto);
        return vehicleRespository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long vehicleId) {
        return vehicleRespository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException(axNotFound));
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
