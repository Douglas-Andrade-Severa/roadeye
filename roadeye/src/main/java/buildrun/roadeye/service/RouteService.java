package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.Route;
import buildrun.roadeye.domain.entity.RouteNumber;

import java.util.List;

public interface RouteService {
    List<Route> getAllRoutes();
}
