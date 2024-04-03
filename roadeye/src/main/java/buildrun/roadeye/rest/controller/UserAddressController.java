package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.UserAddressDto;
import buildrun.roadeye.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/usersAddress", produces = {"application/json"})
@Tag(name = "UserAddress")
@SecurityRequirement(name = "bearer-key")
public class UserAddressController {
    private final UserAddressService userAddressService;
    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @PostMapping
    @Operation(summary = "Insert user and Address", description = "Create a link between user and addresses", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User/Address create"),
    })
    private UserAddressDto createUserAddress(@RequestBody UserAddressDto userAddressDto){
        return userAddressService.createUserAddress(userAddressDto);
    }

    @GetMapping
    @Operation(summary = "Get full user/address", description = "Search all registered users and addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User/Address found successfully"),
    })
    public ResponseEntity<List<UserAddress>> listUsers() {
        return ResponseEntity.ok(userAddressService.getAllUsersAddress());
    }

    @DeleteMapping("/{userAddressId}")
    @Operation(summary = "Delete User/Address by ID", description = "deleted  User/Address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = " User/Address deleted successfully"),
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long userAddressId) {
        userAddressService.deleteUserAddress(userAddressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userAddressId}")
    @Operation(summary = "Update User/Address by ID", description = "The User/Address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User/Address updated successfully"),
    })
    public ResponseEntity<UserAddress> updateUserAddress(@PathVariable Long userAddressId, @Validated @RequestBody UserAddressDto updateUserAddressDto) {
        UserAddress updatedUserAddress = userAddressService.updateUserAddress(userAddressId, updateUserAddressDto);
        return ResponseEntity.ok(updatedUserAddress);
    }

    @GetMapping("/{userAddressId}")
    @Operation(summary = "Get User/Address by ID", description = "Retrieve User/Address information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User/Address ok"),
    })
    public ResponseEntity<UserAddress> getUserById(@PathVariable Long userAddressId) {
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

}
