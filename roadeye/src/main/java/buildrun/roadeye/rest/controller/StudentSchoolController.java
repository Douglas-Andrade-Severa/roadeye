package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.StudentSchool;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.AddressUpdateDto;
import buildrun.roadeye.rest.dto.StudentSchoolDto;
import buildrun.roadeye.service.StudentSchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get School by User ID", description = "Retrieve school information based on the provided UserID. Returns a 404 error if no school are found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User School retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No School found for the provided UserID")
    })
    public ResponseEntity<?> getUserAddressesByUserId(@PathVariable UUID userId) {
        try {
            List<StudentSchool> studentSchools = studentSchoolService.findSchoolByUser_Id(userId);
            return ResponseEntity.ok(studentSchools);
        } catch (EntityNotFoundException ex) {
            ErrorMessage errorMessage = new ErrorMessage("No School found for the user with ID: " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/{studentSchoolId}")
    @Operation(summary = "Delete Student/School by ID", description = "deleted School/Student will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student/School deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseEntity<?> deleteAddress(@PathVariable Long studentSchoolId) {
        return studentSchoolService.deleteStudentSchool(studentSchoolId);
    }

    @PutMapping("/{studentSchoolId}")
    @Operation(summary = "Update Student/School by ID", description = "The Student/School will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student/School updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Student/School not found")
    })
    public ResponseEntity<?> updateStudentSchool(@PathVariable Long studentSchoolId, @RequestBody StudentSchoolDto schoolDto) {
        return studentSchoolService.updateStudentSchool(studentSchoolId, schoolDto);
    }
}
