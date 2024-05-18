package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.enums.PeriodEnum;
import buildrun.roadeye.domain.enums.StudentStatusEnum;

import java.time.LocalDate;
import java.util.Date;

public record StudentRouteDto(Long school, StudentStatusEnum studentStatusEnum, PeriodEnum periodEnum, LocalDate localDate) {
}
