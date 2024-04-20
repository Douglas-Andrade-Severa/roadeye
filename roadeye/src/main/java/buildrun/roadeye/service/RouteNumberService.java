package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.RouteNumber;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteNumberService {
    ResponseEntity<?>  getAllRoutesNumbers();
}
