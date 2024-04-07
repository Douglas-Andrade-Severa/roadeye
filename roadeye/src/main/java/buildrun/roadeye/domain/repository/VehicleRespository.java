package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRespository extends JpaRepository<Vehicle, Long> {
}
