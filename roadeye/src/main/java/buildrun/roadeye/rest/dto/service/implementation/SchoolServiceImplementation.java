package buildrun.roadeye.rest.dto.service.implementation;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.rest.dto.SchoolDto;
import buildrun.roadeye.rest.dto.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
public class SchoolServiceImplementation implements SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolServiceImplementation(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    @Transactional
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
    @Transactional
    public List<School> getAllSchool() {
        return schoolRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSchool(Long schoolId) {
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new EntityNotFoundException("School not found"));
        schoolRepository.delete(school);
    }

    @Override
    @Transactional
    public School updateSchool(Long schoolId, SchoolDto schoolDto) {
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found");
        }
        School school = optionalSchool.get();
        school.setName(schoolDto.name());
        school.setStatusEnum(schoolDto.statusEnum());
        return schoolRepository.save(school);
    }

    @Override
    @Transactional
    public School getSchoolById(Long schoolId) {
        return schoolRepository.findById(schoolId).orElseThrow(() -> new EntityNotFoundException("School not found"));
    }
}
