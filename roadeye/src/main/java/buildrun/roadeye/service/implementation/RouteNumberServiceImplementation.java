package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.RouteNumber;
import buildrun.roadeye.domain.repository.RouteNumberRepository;
import buildrun.roadeye.service.RouteNumberService;
import jakarta.transaction.Transactional;
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
    public List<RouteNumber> getAllRoutesNumbers() {
        return routeNumberRepository.findAll();
    }
}
