package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.SchoolAddressDto;
import buildrun.roadeye.rest.dto.UserAddressDto;
import buildrun.roadeye.service.SchoolAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/schoolAddress", produces = {"application/json"})
@Tag(name = "SchoolAddress")
@SecurityRequirement(name = "bearer-key")
public class SchoolAddressController {
    private final SchoolAddressService schoolAddressService ;

    public SchoolAddressController(SchoolAddressService schoolAddressService) {
        this.schoolAddressService = schoolAddressService;
    }

    @GetMapping
    @Operation(summary = "Get full school/address", description = "Search all registered school and addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School/Address found successfully"),
    })
    public ResponseEntity<List<SchoolAddress>> listSchools() {
        return ResponseEntity.ok(schoolAddressService.getAllSchoolAddress());
    }

    @GetMapping("/{schoolAddressId}")
    @Operation(summary = "Get School/Address by ID", description = "Retrieve School/Address information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School/Address ok"),
    })
    public ResponseEntity<SchoolAddress> getSchoolAddressById(@PathVariable Long schoolAddressId) {
        SchoolAddress schoolAddress = schoolAddressService.getUserAddressById(schoolAddressId);
        return ResponseEntity.ok(schoolAddress);
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get Addresses by School ID", description = "Retrieve address information based on the provided School ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No addresses found for the provided School ID")
    })
    public ResponseEntity<List<SchoolAddress>> getUserAddressesBySchoolId(@PathVariable Long schoolId) {
        List<SchoolAddress> addresses = schoolAddressService.findAddressesBySchool_Id(schoolId);
        if(addresses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/{schoolAddressId}")
    @Operation(summary = "Delete School/Address by ID", description = "deleted  School/Address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = " School/Address deleted successfully"),
    })
    public ResponseEntity<Void> deleteSchoolAddress(@PathVariable Long userAddressId) {
        schoolAddressService.deleteUserAddress(userAddressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{schoolAddressId}")
    @Operation(summary = "Update School/Address by ID", description = "The School/Address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "School/Address updated successfully"),
    })
    public ResponseEntity<SchoolAddress> updateSchoolAddress(@PathVariable Long userAddressId, @Validated @RequestBody SchoolAddressDto updateSchoolAddressDto) {
        SchoolAddress updateSchoolAddress = schoolAddressService.updateSchoolAddress(userAddressId, updateSchoolAddressDto);
        return ResponseEntity.ok(updateSchoolAddress);
    }
}
