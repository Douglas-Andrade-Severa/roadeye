package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.StudentRoute;
import buildrun.roadeye.domain.enums.PeriodEnum;
import buildrun.roadeye.domain.enums.StudentStatusEnum;
import buildrun.roadeye.rest.dto.ImageUpdateRequestDto;
import buildrun.roadeye.rest.dto.StudentRouteDto;
import buildrun.roadeye.rest.dto.StudentRouteUpdateDto;
import buildrun.roadeye.rest.dto.StudentRouteWithAddresses;
import buildrun.roadeye.service.CoordinatesService;
import buildrun.roadeye.service.StudentRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/studentRoute", produces = {"application/json"})
@Tag(name = "Student Route")
@SecurityRequirement(name = "bearer-key")
public class StudentRouteController {
    private final StudentRouteService studentRouteService;
    public StudentRouteController(StudentRouteService studentRouteService) {
        this.studentRouteService = studentRouteService;
    }
    @PostMapping("/{userId}")
    @Operation(summary = "Create Student Route", description = "Enter the route related as user, based on the user ID.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relationship between route and student created successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    public ResponseEntity<?> createStudentRoute(@RequestBody StudentRouteDto routeDtoDto, @PathVariable UUID userId){
        return studentRouteService.createStudentRoute(routeDtoDto, userId);
    }

    @GetMapping
    @Operation(summary = "Get full Student Route", description = "Search all registered Student Route", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student Route found successfully"),
    })
    public ResponseEntity<?> listStudentRoute() {
        return ResponseEntity.ok(studentRouteService.getAllStudentRoute());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get Route by User ID", description = "Retrieve route information based on the provided UserID. Returns a 404 error if no school are found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student route retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No route found for the provided UserID")
    })
    public ResponseEntity<?> getUserAddressesByUserId(@PathVariable UUID userId) {
        try {
            List<StudentRoute> studentRoutes = studentRouteService.findSchoolByUser_Id(userId);
            return ResponseEntity.ok(studentRoutes);
        } catch (EntityNotFoundException ex) {
            ErrorMessage errorMessage = new ErrorMessage("No route found for the user with ID: " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/{studentRouteId}")
    @Operation(summary = "Delete Student/route by ID", description = "deleted Route/Student will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student/Route deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseEntity<?> deleteAddress(@PathVariable Long studentRouteId) {
        return studentRouteService.deleteStudentRoute(studentRouteId);
    }

    @PutMapping("/{studentRouteId}")
    @Operation(summary = "Update Student/Route by ID", description = "The Student/Route will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student/Route updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Student/Route not found")
    })
    public ResponseEntity<?> updateStudentRoute(@PathVariable Long studentRouteId, @RequestBody StudentRouteUpdateDto routeDto) {
        return studentRouteService.updateStudentRoute(studentRouteId, routeDto);
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get Route by school ID", description = "Retrieve route information based on the provided UserID. Returns a 404 error if no school are found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School route retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No route found for the provided SchoolID")
    })
    public ResponseEntity<?> getRouteBySchool(@PathVariable Long schoolId) {
        try {
            List<StudentRoute> studentRoutes = studentRouteService.findSchoolBySchool_Id(schoolId);
            return ResponseEntity.ok(studentRoutes);
        } catch (EntityNotFoundException ex) {
            ErrorMessage errorMessage = new ErrorMessage("No route found for the school with ID: " + schoolId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PutMapping("/{routeId}/updateImage")
    @Operation(summary = "update user imagem", description = "Enter route id and image", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student image was saved successfully."),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    public ResponseEntity<?> updateImage(@RequestBody ImageUpdateRequestDto imageUpdateRequest, @PathVariable Long routeId){
        byte[] imageBytes = Base64.getDecoder().decode(imageUpdateRequest.imageUpdateRequest());
        return studentRouteService.updateStudentRouteImagem(imageBytes, routeId);
    }

    @GetMapping("/routeByPeriodAndDate")
    @Operation(summary = "Get Route by period and date", description = "It will return everything that will go in the period and date informed and ConfimationStudentEnum = ABSENT (User waiting for the bus.) and StudentStatusEnum != IWONTGO(I will not go).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No route found for the provided SchoolID")
    })
    public ResponseEntity<?> getRouteByPeriodAndDate(@RequestParam PeriodEnum periodEnum, @RequestParam StudentStatusEnum studentStatusEnum,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        try {
            List<StudentRouteWithAddresses> studentRoutes = studentRouteService.getStudentRoutesByPeriodAndDate(periodEnum, localDate, studentStatusEnum);
            return ResponseEntity.ok(studentRoutes);
        } catch (EntityNotFoundException ex) {
            ErrorMessage errorMessage = new ErrorMessage("No route found for the period and date: " + periodEnum + "," + localDate+ ","+studentStatusEnum);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
