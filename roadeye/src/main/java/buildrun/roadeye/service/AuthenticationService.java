package buildrun.roadeye.service;

import buildrun.roadeye.rest.dto.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    public String getToken(AuthDto authDto);
    public String validTokenJwt(String token);

}
