package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.enums.ConfimationStudentEnum;
import buildrun.roadeye.domain.enums.StudentStatus;
import buildrun.roadeye.domain.enums.StatusEnum;

public record StudentRouteDto(Long school, StudentStatus studentStatus) {
}
