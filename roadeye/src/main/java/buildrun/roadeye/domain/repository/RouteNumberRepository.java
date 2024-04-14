package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.RouteNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteNumberRepository extends JpaRepository<RouteNumber, Long> {
}
