package buildrun.roadeye.rest.dto.service.implementation;

import buildrun.roadeye.domain.entity.ErrorResponse;
import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.rest.dto.SchoolDto;
import buildrun.roadeye.rest.dto.service.SchoolService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SchoolServiceImplementation implements SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolServiceImplementation(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public SchoolDto createSchool(SchoolDto schoolDto) {
        var schoolFromName = schoolRepository.findByName(schoolDto.name());
        if (schoolFromName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The name of the school already exists.");
        }
        School school = new School();
        school.setName(schoolDto.name());
        school.setStatusEnum(schoolDto.statusEnum());
        school = schoolRepository.save(school);
        return schoolDto.fromEntity(school);
    }

    @Override
    public ResponseEntity<?> getAllSchool() {
        List<School> schools = schoolRepository.findAll();
        if (!schools.isEmpty()) {
            return ResponseEntity.ok(schools);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No schools found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> deleteSchool(Long schoolId) {
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isPresent()) {
            schoolRepository.deleteById(schoolId);
            ErrorResponse errorResponse = new ErrorResponse("School deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("School does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @Override
    public ResponseEntity<?> updateSchool(Long schoolId, SchoolDto schoolDto) {
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isPresent()) {
            School school = optionalSchool.get();
            school.setName(schoolDto.name());
            school.setStatusEnum(schoolDto.statusEnum());
            return ResponseEntity.ok(schoolRepository.save(school));
        }else {
            ErrorResponse errorResponse = new ErrorResponse("School does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getSchoolById(Long schoolId) {
        School school = schoolRepository.findById(schoolId).orElse(null);
        if (school != null) {
            return ResponseEntity.ok(school);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("School does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
