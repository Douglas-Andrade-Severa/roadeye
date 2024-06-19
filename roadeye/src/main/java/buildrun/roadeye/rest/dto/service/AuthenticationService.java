package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.rest.dto.LoginRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    String getToken(LoginRequest authDto);
    String validTokenJwt(String token);
}
