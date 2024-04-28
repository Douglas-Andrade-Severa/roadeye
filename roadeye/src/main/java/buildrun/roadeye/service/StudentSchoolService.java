package buildrun.roadeye.service;

import buildrun.roadeye.rest.dto.StudentSchoolDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface StudentSchoolService {
    ResponseEntity<?> getAllStudentSchool();

    ResponseEntity<?> createSchoolUser(StudentSchoolDto schoolDto, UUID userId);
}
