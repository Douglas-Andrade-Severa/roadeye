package buildrun.roadeye.rest.dto.service.implementation;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.service.AuthenticationService;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.LoginRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
    private final UserRepository userRepository;
    public AuthenticationServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    @Override
    public String getToken(LoginRequest authDto) {
        User byLogin = userRepository.findByLogin(authDto.login())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + authDto.login()));
        return generateToken(byLogin);
    }


    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret-api-douglas-andrade");
            return JWT.create()
                    .withIssuer("roadeye-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpiryDate())
                    .sign(algorithm);

        }catch (JWTCreationException exception){
            throw new RuntimeException("Error when trying to generate the token!"+exception.getMessage());
        }
    }

    public String validTokenJwt(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret-api-douglas-andrade");
            return JWT.require(algorithm)
                    .withIssuer("roadeye-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant generateExpiryDate() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }
}
