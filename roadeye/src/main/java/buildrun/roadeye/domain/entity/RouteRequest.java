package buildrun.roadeye.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class RouteRequest {
    private Location origin;
    private Location destination;
    private List<Location> intermediates;
    private String travelMode;
    private boolean optimizeWaypointOrder;
}
