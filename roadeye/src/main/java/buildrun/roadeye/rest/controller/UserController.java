package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/users", produces = {"application/json"})
@SecurityRequirement(name = "roadeyeApi")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @Validated @RequestBody UserDto updateUserDto) {
        User updatedUser = userService.updateUser(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity<User> updateUserPassword(@PathVariable UUID userId, @Validated @RequestBody UserDto updateUserDto) {
        User updatedUser = userService.updateUserPassword(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
