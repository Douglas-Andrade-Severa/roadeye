package buildrun.roadeye.domain.enums.service.implementation;

import buildrun.roadeye.domain.entity.ErrorResponse;
import buildrun.roadeye.domain.entity.RouteNumber;
import buildrun.roadeye.domain.repository.RouteNumberRepository;
import buildrun.roadeye.domain.enums.service.RouteNumberService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RouteNumberServiceImplementation implements RouteNumberService {
    private final RouteNumberRepository routeNumberRepository;

    public RouteNumberServiceImplementation(RouteNumberRepository routeNumberRepository) {
        this.routeNumberRepository = routeNumberRepository;
    }

    @Override
    public ResponseEntity<?> getAllRoutesNumbers() {
        List<RouteNumber> routeNumbers = routeNumberRepository.findAll();
        if (!routeNumbers.isEmpty()) {
            return ResponseEntity.ok(routeNumbers);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No routeNumbers found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
