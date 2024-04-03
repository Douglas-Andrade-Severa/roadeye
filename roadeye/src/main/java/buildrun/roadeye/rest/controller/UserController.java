package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "User")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create user", description = "Insert user.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User create"),
    })
    @PostMapping
    private UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping
    @Operation(summary = "Get full users", description = "Search all registered users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "users found successfully"),
    })
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID", description = "deleted user will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "user deleted successfully"),
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user by ID", description = "The user will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "user updated successfully"),
    })
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @Validated @RequestBody UserDto updateUserDto) {
        User updatedUser = userService.updateUser(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/password/{userId}")
    @Operation(summary = "Update user password by ID", description = "The user password will be updated based on the user ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User password has been updated successfully"),
    })
    public ResponseEntity<User> updateUserPassword(@PathVariable UUID userId, @Validated @RequestBody UserDto updateUserDto) {
        User updatedUser = userService.updateUserPassword(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve user information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ok"),
    })
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
