package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.LoginRequest;
import buildrun.roadeye.rest.dto.LoginResponse;
import buildrun.roadeye.rest.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/login", produces = {"application/json"})
@Tag(name = "Authentication")
@SecurityRequirement(name = "bearer-key")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }
    @PostMapping
    @Operation(summary = "Post user by Login and Password", description = "Retrieves user information based on the provided Login and Password.")//security = { @SecurityRequirement(name = "bearer-key") }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ok, welcome"),
    })

    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByLogin(loginRequest.login());
        if (userOptional.isEmpty() || !userOptional.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }
        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        authenticationManager.authenticate(userAuthenticationToken);
        return ResponseEntity.ok(new LoginResponse(authenticationService.getToken(loginRequest), userOptional.get().getId(), userOptional.get().getRole()));
    }

}
