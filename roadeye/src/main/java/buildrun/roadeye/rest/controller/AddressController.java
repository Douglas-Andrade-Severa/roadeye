package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.rest.dto.AddressDto;
import buildrun.roadeye.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping
    private AddressDto createUser(@RequestBody AddressDto addressDto){
        return addressService.createAddress(addressDto);
    }
    @GetMapping
    public ResponseEntity<List<Address>> listAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Address> updateUser(@PathVariable Long addressId, @Validated @RequestBody AddressDto addressDto) {
        Address updatedAddress = addressService.updateAddress(addressId, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getUserById(@PathVariable Long addressId) {
        Address address = addressService.getUserById(addressId);
        return ResponseEntity.ok(address);
    }
}
