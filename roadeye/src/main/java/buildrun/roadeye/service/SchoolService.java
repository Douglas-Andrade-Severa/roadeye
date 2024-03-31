package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.rest.dto.SchoolDto;

import java.util.List;

public interface SchoolService {
    SchoolDto createSchool(SchoolDto schoolDto);

    List<School> getAllSchool();

    void deleteSchool(Long schoolId);

    School updateSchool(Long schoolId, SchoolDto schoolDto);

    School getSchoolById(Long schoolId);
}
