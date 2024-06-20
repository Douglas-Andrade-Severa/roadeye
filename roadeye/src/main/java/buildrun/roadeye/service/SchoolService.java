package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.rest.dto.SchoolDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SchoolService {
    SchoolDto createSchool(SchoolDto schoolDto);

    ResponseEntity<?> getAllSchool();

    ResponseEntity<?> deleteSchool(Long schoolId);

    ResponseEntity<?> updateSchool(Long schoolId, SchoolDto schoolDto);

    ResponseEntity<?> getSchoolById(Long schoolId);
}
