package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.StudentSchool;
import buildrun.roadeye.rest.dto.StudentSchoolDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface StudentSchoolService {
    ResponseEntity<?> getAllStudentSchool();

    ResponseEntity<?> createSchoolUser(StudentSchoolDto schoolDto, UUID userId);

    List<StudentSchool> findSchoolByUser_Id(UUID userId);

    ResponseEntity<?> deleteStudentSchool(Long studentSchoolId);

    ResponseEntity<?> updateStudentSchool(Long studentSchoolId, StudentSchoolDto schoolDto);
}
