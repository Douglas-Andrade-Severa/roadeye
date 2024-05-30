package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.*;
import buildrun.roadeye.service.AddressService;
import buildrun.roadeye.service.SchoolAddressService;
import buildrun.roadeye.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/address", produces = {"application/json"})
@Tag(name = "Address")
@SecurityRequirement(name = "bearer-key")
public class AddressController {
    private final AddressService addressService;
    private final UserAddressService userAddressService;
    private final SchoolAddressService schoolAddressService;
    public AddressController(AddressService addressService, UserAddressService userAddressService, SchoolAddressService schoolAddressService) {
        this.addressService = addressService;
        this.userAddressService = userAddressService;
        this.schoolAddressService = schoolAddressService;
    }
    @GetMapping
    @Operation(summary = "Get full address", description = "Search all registered addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "addresses found successfully"),
    })
    public ResponseEntity<?>  listAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "Delete Address by ID", description = "deleted address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "address deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        return addressService.deleteAddress(addressId);
    }
    @PutMapping("/{addressId}")
    @Operation(summary = "Update Address by ID", description = "The address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "address updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody AddressUpdateDto addressUpdateDto) {
        return addressService.updateAddress(addressId, addressUpdateDto);
    }

    @GetMapping("/{addressId}")
    @Operation(summary = "Get Address by ID", description = "Retrieve address information based on the provided ID...", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address ok"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        return addressService.getAddressResponseById(addressId);
    }

    // ***************************************************** User *****************************************************
    @PostMapping("/user/{userId}")
    @Operation(summary = "Create user address", description = "Enter address for user based on id provided", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created for user"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    public ResponseEntity<?> createAddressByUser(@RequestBody AddressDto addressDto, @PathVariable UUID userId){
        return addressService.createAddressByUser(addressDto, userId);
    }
    @GetMapping("/user")
    @Operation(summary = "Get full user/address", description = "Search all registered users and addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User/Address found successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "User/Address not found")
    })
    public ResponseEntity<?> listUsersAddress() {
        return ResponseEntity.ok(userAddressService.getAllUsersAddress());
    }

    @DeleteMapping("/user/{userAddressId}")
    @Operation(summary = "Delete User/Address by ID", description = "deleted  User/Address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User/Address deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "User/Address not found")
    })
    public ResponseEntity<?> deleteUserAddress(@PathVariable Long userAddressId) {
        return userAddressService.deleteUserAddress(userAddressId);
    }

    @PutMapping("/user/{userAddressId}")
    @Operation(summary = "Update User/Address by ID", description = "The User/Address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User/Address updated successfully"),
    })
    public ResponseEntity<?> updateUserAddress(@PathVariable Long userAddressId, @Validated @RequestBody UserAddressDto updateUserAddressDto) {
        return userAddressService.updateUserAddress(userAddressId, updateUserAddressDto);
    }


    @GetMapping("/user/{userAddressId}")
    @Operation(summary = "Get User/Address by ID", description = "Retrieve User/Address information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User/Address ok"),
    })
    public ResponseEntity<?> getUserAddressById(@PathVariable Long userAddressId) {
        return userAddressService.getUserAddressById(userAddressId);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get Addresses by User ID", description = "Retrieve address information based on the provided UserID. Returns a 404 error if no addresses are found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No addresses found for the provided UserID")
    })
    public ResponseEntity<?> getUserAddressesByUserId(@PathVariable UUID userId) {
        try {
            List<UserAddress> addresses = userAddressService.findAddressesByUser_Id(userId);
            return ResponseEntity.ok(addresses);
        } catch (EntityNotFoundException ex) {
            ErrorMessage errorMessage = new ErrorMessage("No addresses found for the user with ID: " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PutMapping("/user/activateDisableAddress/{userId}")
    @Operation(summary = "Enable or disable addresses", description = "Enter the address id and user id.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created for user"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    public ResponseEntity<?> activateDisableAddressByUser(@RequestBody AddressActivateDisableDto activateDisable, @PathVariable UUID userId){
        return addressService.updateActivateDisableAddressByUser(activateDisable, userId);
    }


    // ***************************************************** School *****************************************************
    @PostMapping("/school/{schoolId}")
    @Operation(summary = "Create School address", description = "Enter address for school based on id provided.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created for school"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "School/Address not found")
    })
    public ResponseEntity<?> createAddressBySchool(@RequestBody AddressDto addressDto, @PathVariable Long schoolId){
        return addressService.createAddressBySchool(addressDto, schoolId);
    }
    @GetMapping("/school")
    @Operation(summary = "Get full school/address", description = "Search all registered school and addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School/Address found successfully"),
    })
    public ResponseEntity<?> listSchools() {
        return ResponseEntity.ok(schoolAddressService.getAllSchoolAddress());
    }

    @GetMapping("/school/{schoolAddressId}")
    @Operation(summary = "Get School/Address by ID", description = "Retrieve School/Address information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School/Address ok"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "School/Address not found")
    })
    public ResponseEntity<?> getSchoolAddressById(@PathVariable Long schoolAddressId) {
        return schoolAddressService.getSchoolAddressById(schoolAddressId);
    }

    @GetMapping("/schools/{schoolId}")
    @Operation(summary = "Get Addresses by School ID", description = "Retrieve address information based on the provided School ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School Addresses retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "School by ID not found")
    })
    public ResponseEntity<?> getUserAddressesBySchoolId(@PathVariable Long schoolId) {
        try{
            List<SchoolAddress> addresses = schoolAddressService.findAddressesBySchool_Id(schoolId);
            return ResponseEntity.ok(addresses);
        } catch (EntityNotFoundException ex) {
            ErrorMessage errorMessage = new ErrorMessage("No addresses found for the School with ID: " + schoolId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/school/{schoolAddressId}")
    @Operation(summary = "Delete School/Address by ID", description = "deleted  School/Address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "School/Address deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "School/Address not found")
    })
    public ResponseEntity<?> deleteSchoolAddress(@PathVariable Long schoolAddressId) {
        return schoolAddressService.deleteUserAddress(schoolAddressId);
    }

    @PutMapping("/school/{schoolAddressId}")
    @Operation(summary = "Update School/Address by ID", description = "The School/Address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "School/Address updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "School/Address not found")
    })
    public ResponseEntity<?> updateSchoolAddress(@PathVariable Long schoolAddressId, @Validated @RequestBody SchoolAddressDto updateSchoolAddressDto) {
        return schoolAddressService.updateSchoolAddress(schoolAddressId, updateSchoolAddressDto);
    }
}
