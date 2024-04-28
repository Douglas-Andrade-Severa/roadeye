package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.StudentSchoolDto;
import buildrun.roadeye.service.StudentSchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/studentSchool", produces = {"application/json"})
@Tag(name = "Student School")
@SecurityRequirement(name = "bearer-key")
public class StudentSchoolController {
    private final StudentSchoolService studentSchoolService;

    public StudentSchoolController(StudentSchoolService studentSchoolService) {
        this.studentSchoolService = studentSchoolService;
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create Student School", description = "Enter the school related as user, based on the user ID.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relationship between school and student created successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    private ResponseEntity<?> createSchoolUser(@RequestBody StudentSchoolDto schoolDto, @PathVariable UUID userId){
        return studentSchoolService.createSchoolUser(schoolDto, userId);
    }

    @GetMapping
    @Operation(summary = "Get full Student School", description = "Search all registered Student School", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student School found successfully"),
    })
    public ResponseEntity<?> listStudentSchool() {
        return ResponseEntity.ok(studentSchoolService.getAllStudentSchool());
    }
}
