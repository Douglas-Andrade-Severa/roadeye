package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.AddressDto;
import buildrun.roadeye.rest.dto.SchoolAddressDto;
import buildrun.roadeye.rest.dto.UserAddressDto;
import buildrun.roadeye.service.AddressService;
import buildrun.roadeye.service.SchoolAddressService;
import buildrun.roadeye.service.UserAddressService;
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
    public ResponseEntity<List<Address>> listAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "Delete Address by ID", description = "deleted address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "address deleted successfully"),
    })
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update Address by ID", description = "The address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "address updated successfully"),
    })
    public ResponseEntity<Address> updateUser(@PathVariable Long addressId, @Validated @RequestBody AddressDto addressDto) {
        Address updatedAddress = addressService.updateAddress(addressId, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @GetMapping("/{addressId}")
    @Operation(summary = "Get Address by ID", description = "Retrieve address information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address ok"),
    })
    public ResponseEntity<Address> getUserById(@PathVariable Long addressId) {
        Address address = addressService.getUserById(addressId);
        return ResponseEntity.ok(address);
    }

    //User
    @PostMapping("/user/{userId}")
    @Operation(summary = "Create user address", description = "Enter address for user based on id provided", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created for user"),
    })
    private AddressDto createAddressByUser(@RequestBody AddressDto addressDto, @PathVariable UUID userId){
        return addressService.createAddressByUser(addressDto, userId);
    }
    @GetMapping("/user")
    @Operation(summary = "Get full user/address", description = "Search all registered users and addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User/Address found successfully"),
    })
    public ResponseEntity<List<UserAddress>> listUsersAddress() {
        return ResponseEntity.ok(userAddressService.getAllUsersAddress());
    }

    @DeleteMapping("/user/{userAddressId}")
    @Operation(summary = "Delete User/Address by ID", description = "deleted  User/Address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = " User/Address deleted successfully"),
    })
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long userAddressId) {
        userAddressService.deleteUserAddress(userAddressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{userAddressId}")
    @Operation(summary = "Update User/Address by ID", description = "The User/Address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User/Address updated successfully"),
    })
    public ResponseEntity<UserAddress> updateUserAddress(@PathVariable Long userAddressId, @Validated @RequestBody UserAddressDto updateUserAddressDto) {
        UserAddress updatedUserAddress = userAddressService.updateUserAddress(userAddressId, updateUserAddressDto);
        return ResponseEntity.ok(updatedUserAddress);
    }
    @GetMapping("/user/{userAddressId}")
    @Operation(summary = "Get User/Address by ID", description = "Retrieve User/Address information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User/Address ok"),
    })
    public ResponseEntity<UserAddress> getUserAddressById(@PathVariable Long userAddressId) {
        UserAddress userAddress = userAddressService.getUserAddressById(userAddressId);
        return ResponseEntity.ok(userAddress);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get Addresses by UserID", description = "Retrieve address information based on the provided UserID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No addresses found for the provided UserID")
    })
    public ResponseEntity<List<UserAddress>> getUserAddressesByUserId(@PathVariable UUID userId) {
        List<UserAddress> addresses = userAddressService.findAddressesByUser_Id(userId);
        if(addresses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addresses);
    }

    //School
    @PostMapping("/school/{schoolId}")
    @Operation(summary = "Create School address", description = "Enter address for school based on id provided.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created for school"),
    })
    private AddressDto createAddressBySchool(@RequestBody AddressDto addressDto, @PathVariable Long schoolId){
        return addressService.createAddressBySchool(addressDto, schoolId);
    }
    @GetMapping("/school")
    @Operation(summary = "Get full school/address", description = "Search all registered school and addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "School/Address found successfully"),
    })
    public ResponseEntity<List<SchoolAddress>> listSchools() {
        return ResponseEntity.ok(schoolAddressService.getAllSchoolAddress());
    }

    @GetMapping("/school/{schoolAddressId}")
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

    @DeleteMapping("/school/{schoolAddressId}")
    @Operation(summary = "Delete School/Address by ID", description = "deleted  School/Address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = " School/Address deleted successfully"),
    })
    public ResponseEntity<Void> deleteSchoolAddress(@PathVariable Long userAddressId) {
        schoolAddressService.deleteUserAddress(userAddressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/school/{schoolAddressId}")
    @Operation(summary = "Update School/Address by ID", description = "The School/Address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "School/Address updated successfully"),
    })
    public ResponseEntity<SchoolAddress> updateSchoolAddress(@PathVariable Long userAddressId, @Validated @RequestBody SchoolAddressDto updateSchoolAddressDto) {
        SchoolAddress updateSchoolAddress = schoolAddressService.updateSchoolAddress(userAddressId, updateSchoolAddressDto);
        return ResponseEntity.ok(updateSchoolAddress);
    }
}
