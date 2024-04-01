package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.rest.dto.AddressDto;
import buildrun.roadeye.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/address", produces = {"application/json"})
@SecurityRequirement(name = "roadeyeApi")
@Tag(name = "Address")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping
    @Operation(summary = "Create Address", description = "Insert address.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address create"),
    })
    private AddressDto createUser(@RequestBody AddressDto addressDto){
        return addressService.createAddress(addressDto);
    }
    @GetMapping
    @Operation(summary = "Get full address", description = "Search all registered addresses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "addresses found successfully"),
    })
    public ResponseEntity<List<Address>> listAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }
    @Operation(summary = "Delete Address by ID", description = "deleted address will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "address deleted successfully"),
    })
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Address by ID", description = "The address will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "address updated successfully"),
    })
    @PutMapping("/{userId}")
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
}
