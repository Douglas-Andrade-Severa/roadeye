package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.service.SchoolAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/schoolAddress", produces = {"application/json"})
@Tag(name = "UserAddress")
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
}
