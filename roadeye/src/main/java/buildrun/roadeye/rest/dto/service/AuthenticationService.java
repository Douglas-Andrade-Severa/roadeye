package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.rest.dto.LoginRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    public String getToken(LoginRequest authDto);
    public String validTokenJwt(String token);

}
