package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
