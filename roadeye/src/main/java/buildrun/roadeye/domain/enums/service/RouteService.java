package buildrun.roadeye.domain.enums.service;

import buildrun.roadeye.domain.entity.Route;
import buildrun.roadeye.domain.entity.RouteNumber;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteService {
    ResponseEntity<?> getAllRoutes();
}
