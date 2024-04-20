package buildrun.roadeye.domain.enums.service.implementation;

import buildrun.roadeye.domain.entity.ErrorResponse;
import buildrun.roadeye.domain.entity.Route;
import buildrun.roadeye.domain.repository.RouteRepository;
import buildrun.roadeye.domain.enums.service.RouteService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RouteServiceImplementation implements RouteService {
    private final RouteRepository routeRepository;

    public RouteServiceImplementation(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public ResponseEntity<?> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        if (!routes.isEmpty()) {
            return ResponseEntity.ok(routes);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No routes found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
