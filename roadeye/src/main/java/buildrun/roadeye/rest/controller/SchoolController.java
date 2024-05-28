package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.SchoolDto;
import buildrun.roadeye.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Create school", description = "Insert school.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "School create"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    public SchoolDto createSchool(@RequestBody SchoolDto schoolDto){
        return schoolService.createSchool(schoolDto);
    }

    @GetMapping
    @Operation(summary = "Get full schools", description = "Search all registered schools", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "schools found successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    public ResponseEntity<?> listSchool() {
        return ResponseEntity.ok(schoolService.getAllSchool());
    }

    @DeleteMapping("/{schoolId}")
    @Operation(summary = "Delete school by ID", description = "deleted school will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "school deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "schools not found")
    })
    public ResponseEntity<?> deleteScholl(@PathVariable Long schoolId) {
        return schoolService.deleteSchool(schoolId);
    }

    @PutMapping("/{schoolId}")
    @Operation(summary = "Update school by ID", description = "The school will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "school updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "schools not found")
    })
    public ResponseEntity<?> updateSchool(@PathVariable Long schoolId, @Validated @RequestBody SchoolDto schoolDto) {
        return schoolService.updateSchool(schoolId, schoolDto);
    }

    @GetMapping("/{schoolId}")
    @Operation(summary = "Get school by ID", description = "Retrieve school information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "school ok"),
    })
    public ResponseEntity<?> getSchoolById(@PathVariable Long schoolId) {
        return schoolService.getSchoolById(schoolId);
    }
}
