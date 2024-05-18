package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.entity.StudentRoute;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.ConfimationStudentEnum;
import buildrun.roadeye.domain.enums.PeriodEnum;
import buildrun.roadeye.domain.enums.StudentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface StudentRouteRepository extends JpaRepository<StudentRoute, Long> {
    List<StudentRoute> findByUser_Id(UUID userId);
    List<StudentRoute> findBySchool_Id(Long school);
    List<StudentRoute> findByPeriodEnumAndLocalDate(PeriodEnum periodEnum, LocalDate localDate);
    List<StudentRoute> findByPeriodEnumAndLocalDateAndConfimationStudentEnumAndStudentStatusEnumNot(
            PeriodEnum periodEnum,
            LocalDate localDate,
            ConfimationStudentEnum confimationStudentEnum,
            StudentStatusEnum studentStatusEnum);
}
