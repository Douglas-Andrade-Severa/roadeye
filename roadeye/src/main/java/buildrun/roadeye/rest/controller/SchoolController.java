package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.rest.dto.SchoolDto;
import buildrun.roadeye.service.SchoolService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/school", produces = {"application/json"})
@Tag(name = "School")
@SecurityRequirement(name = "bearer-key")
public class SchoolController {
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    private SchoolDto createSchool(@RequestBody SchoolDto schoolDto){
        return schoolService.createSchool(schoolDto);
    }

    @GetMapping
    public ResponseEntity<List<School>> listSchool() {
        return ResponseEntity.ok(schoolService.getAllSchool());
    }

    @DeleteMapping("/{schoolId}")
    public ResponseEntity<Void> deleteScholl(@PathVariable Long schoolId) {
        schoolService.deleteSchool(schoolId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{schoolId}")
    public ResponseEntity<School> updateSchool(@PathVariable Long schoolId, @Validated @RequestBody SchoolDto schoolDto) {
        School school = schoolService.updateSchool(schoolId, schoolDto);
        return ResponseEntity.ok(school);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long schoolId) {
        School school = schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(school);
    }
}
