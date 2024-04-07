package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRespository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByNumberRenavan(String numberRenavan);


    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
