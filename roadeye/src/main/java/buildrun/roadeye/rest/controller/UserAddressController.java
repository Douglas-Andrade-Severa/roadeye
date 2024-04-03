package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.UserAddressDto;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserAddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final UserAddressService UseraddressService;
    public UserAddressController(UserAddressService UseraddressService) {
        this.UseraddressService = UseraddressService;
    }

    @PostMapping
    private UserAddressDto createUserAddress(@RequestBody UserAddressDto userAddressDto){
        return UseraddressService.createUserAddress(userAddressDto);
    }

    @GetMapping
    public ResponseEntity<List<UserAddress>> listUsers() {
        return ResponseEntity.ok(UseraddressService.getAllUsersAddress());
    }

    @DeleteMapping("/{userAddressId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userAddressId) {
        UseraddressService.deleteUserAddress(userAddressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userAddressId}")
    public ResponseEntity<UserAddress> updateUserAddress(@PathVariable Long userAddressId, @Validated @RequestBody UserAddressDto updateUserAddressDto) {
        UserAddress updatedUserAddress = UseraddressService.updateUserAddress(userAddressId, updateUserAddressDto);
        return ResponseEntity.ok(updatedUserAddress);
    }

    @GetMapping("/{userAddressId}")
    public ResponseEntity<UserAddress> getUserById(@PathVariable Long userAddressId) {
        UserAddress userAddress = UseraddressService.getUserAddressById(userAddressId);
        return ResponseEntity.ok(userAddress);
    }
}
