package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.entity.StudentRoute;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.ConfimationStudentEnum;
import buildrun.roadeye.domain.enums.PeriodEnum;
import buildrun.roadeye.domain.enums.StudentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface StudentRouteRepository extends JpaRepository<StudentRoute, Long> {
    List<StudentRoute> findByUser_Id(UUID userId);
    List<StudentRoute> findBySchool_Id(Long school);
    List<StudentRoute> findByPeriodEnumAndLocalDate(PeriodEnum periodEnum, LocalDate localDate);

    @Query(value = "SELECT sr.*, " +
            "aschool.street as school_street, aschool.city as school_city, aschool.state as school_state, aschool.post_code as school_zip_code, aschool.latitude as school_latitude,aschool.longitude as school_longitude, aschool.address_id as school_id, aschool.neighborhood as school_neighborhood,aschool.country as school_country,aschool.complement as school_complement,aschool.number as school_number,aschool.status as school_statusEnum," +
            "asuser.street as user_street, asuser.city as user_city, asuser.state as user_state, asuser.post_code as user_zip_code, asuser.latitude as user_latitude, asuser.longitude as user_longitude, asuser.address_id as user_id, asuser.neighborhood as user_neighborhood, asuser.country as user_country,asuser.complement as user_complement, asuser.number as user_number,asuser.status as user_statusEnum " +
            "FROM tb_route sr " +
            "INNER JOIN tb_school_address sa ON sa.school_id = sr.school_id " +
            "INNER JOIN tb_address aschool ON aschool.address_id = sa.address_id " +
            "INNER JOIN tb_user_address ua ON ua.user_id = sr.user_id " +
            "INNER JOIN tb_address asuser ON asuser.address_id = ua.address_id " +
            "INNER JOIN tb_users userid on userid.user_id = sr.user_id "+
            "WHERE sr.period = :periodEnum " +
            "AND sr.local_date = :localDate " +
            "AND sr.status_confirmation_student = :confimationStudentEnum " +
            "AND sr.status_student <> :studentStatusEnum",
            nativeQuery = true)
    List<Object[]> findWithAddressesByPeriodEnumAndLocalDateAndConfimationStudentEnumAndStudentStatusEnumNot(
            @Param("periodEnum") String periodEnum,
            @Param("localDate") LocalDate localDate,
            @Param("confimationStudentEnum") String confimationStudentEnum,
            @Param("studentStatusEnum") String studentStatusEnum);
}
