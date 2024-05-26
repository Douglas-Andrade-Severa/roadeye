package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.entity.StudentRoute;
import buildrun.roadeye.domain.entity.User;

public record StudentRouteWithAddresses(StudentRoute studentRoute,
                                        Address schoolAddress,
                                        Address userAddress) {
}
