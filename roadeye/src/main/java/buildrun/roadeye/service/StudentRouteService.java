package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.StudentRoute;
import buildrun.roadeye.domain.enums.PeriodEnum;
import buildrun.roadeye.rest.dto.StudentRouteDto;
import buildrun.roadeye.rest.dto.StudentRouteUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface StudentRouteService {
    ResponseEntity<?> getAllStudentRoute();

    ResponseEntity<?> createStudentRoute(StudentRouteDto schoolDto, UUID userId);

    List<StudentRoute> findSchoolByUser_Id(UUID userId);

    ResponseEntity<?> deleteStudentRoute(Long studentSchoolId);

    ResponseEntity<?> updateStudentRoute(Long studentSchoolId, StudentRouteUpdateDto schoolDto);

    List<StudentRoute> findSchoolBySchool_Id(Long schoolId);

    ResponseEntity<?> updateStudentRouteImagem(MultipartFile file, Long routeId);

    List<StudentRoute> getStudentRoutesByPeriodAndDate(PeriodEnum periodEnum, LocalDate localDate);
}
