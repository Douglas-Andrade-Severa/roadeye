package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.FatherSonRelationshipDto;
import buildrun.roadeye.rest.dto.service.FatherSonRelationshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fatherSonRelationship", produces = {"application/json"})
@Tag(name = "Father Son Relationship")
@SecurityRequirement(name = "bearer-key")
public class FatherSonRelationshipController {
    private final FatherSonRelationshipService fatherSonRelationshipService;

    public FatherSonRelationshipController(FatherSonRelationshipService fatherSonRelationshipService) {
        this.fatherSonRelationshipService = fatherSonRelationshipService;
    }

    @PostMapping
    @Operation(summary = "Create Father Son Relationship", description = "Insert fatherSonRelationship.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Father Son Relationship create"),
    })
    public ResponseEntity<?> fatherSonRelationshipDto(@RequestBody FatherSonRelationshipDto fatherSonRelationship){
        return fatherSonRelationshipService.createFatherSonRelationship(fatherSonRelationship);
    }


    @GetMapping
    @Operation(summary = "Get full responsible/student", description = "Search all registered responsible and student", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsible/student found successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Responsible/student not found")
    })
    public ResponseEntity<?> listFatherSonRelationship() {
        return ResponseEntity.ok(fatherSonRelationshipService.getAllFatherSonRelationship());
    }

    @GetMapping("/{fatherSonRelationshipId}")
    @Operation(summary = "Get responsible/student by ID", description = "Retrieve responsible/student information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "responsible/student ok"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Responsible/student not found")
    })
    public ResponseEntity<?> getFatherSonRelationshipById(@PathVariable Long fatherSonRelationshipId) {
        return fatherSonRelationshipService.getFatherSonRelationshipById(fatherSonRelationshipId);
    }

    @Operation(summary = "Get father son relationship by UserId", description = "Retrieve relationship information between guardian and child based on the UserID provided. It can be the student's or guardian's ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Addresses retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Responsible/student not found")
    })
    public ResponseEntity<?> getFatherSonRelationshipByUserId(@Validated @RequestBody FatherSonRelationshipDto fatherSonRelationshipDto) {
        return fatherSonRelationshipService.findFatherSonRelationshipByUserId(fatherSonRelationshipDto);
    }

    @DeleteMapping("/{fatherSonRelationshipId}")
    @Operation(summary = "Delete responsible/student by ID", description = "deleted responsible/student will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "responsible/student deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Responsible/student not found")
    })
    public ResponseEntity<?> deleteFatherSonRelationship(@PathVariable Long fatherSonRelationshipId) {
        return fatherSonRelationshipService.deleteFatherSonRelationship(fatherSonRelationshipId);
    }

    @PutMapping("/{fatherSonRelationshipId}")
    @Operation(summary = "Update responsible/student by ID", description = "The responsible/student will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "responsible/student updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Responsible/student not found")
    })
    public ResponseEntity<?> FatherSonRelationship(@PathVariable Long fatherSonRelationshipId, @Validated @RequestBody FatherSonRelationshipDto fatherSonRelationshipDto) {
        return fatherSonRelationshipService.updateFatherSonRelationship(fatherSonRelationshipId, fatherSonRelationshipDto);
    }
}
