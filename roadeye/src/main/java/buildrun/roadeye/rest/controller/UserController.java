package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.rest.dto.UserPasswordDto;
import buildrun.roadeye.rest.dto.UserUpdateDto;
import buildrun.roadeye.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @Operation(summary = "Create user", description = "Insert user.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User create"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource")
    })
    private UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping
    @Operation(summary = "Get full users", description = "Search all registered users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "users found successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "user not found")
    })
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID", description = "deleted user will be deleted based on id.", method = "DEL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "user deleted successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user by ID", description = "The user will be updated based on the ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "user updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @Validated @RequestBody UserUpdateDto updateUserDto) {
        return userService.updateUser(userId, updateUserDto);
    }

    @PutMapping("/password/{userId}")
    @Operation(summary = "Update user password by ID", description = "The user password will be updated based on the user ID.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User password has been updated successfully"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> updateUserPassword(@PathVariable UUID userId, @Validated @RequestBody UserPasswordDto userPasswordDtoDto) {
        return userService.updateUserPassword(userId, userPasswordDtoDto);
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve user information based on the provided ID.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User ok"),
            @ApiResponse(responseCode = "403", description = "The client is authenticated, but does not have permission to access the requested resource"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        return userService.getUserResponseById(userId);
    }
}
