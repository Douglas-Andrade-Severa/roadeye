package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.Route;
import buildrun.roadeye.domain.repository.RouteRepository;
import buildrun.roadeye.service.RouteService;
import jakarta.transaction.Transactional;
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
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
}
