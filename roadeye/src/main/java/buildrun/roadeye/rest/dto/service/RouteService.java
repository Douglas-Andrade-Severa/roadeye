package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Route;
import buildrun.roadeye.domain.entity.RouteNumber;

import java.util.List;

public interface RouteService {
    List<Route> getAllRoutes();
}
