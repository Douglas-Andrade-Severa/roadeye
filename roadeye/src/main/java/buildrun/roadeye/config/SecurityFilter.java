package buildrun.roadeye.config;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Autowired
    public SecurityFilter(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        boolean isWhitelisted = false;
        for (String path : AUTH_WHITELIST) {
            if (requestURI.startsWith(path)) {
                isWhitelisted = true;
                break;
            }
        }

        if (isWhitelisted) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractsTokenHeader(request);
        if(token != null){
            String login = authenticationService.validTokenJwt(token);
            Optional<User> userOptional = userRepository.findByLogin(login);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            }
        }
        filterChain.doFilter(request,response);
    }

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public String extractsTokenHeader(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        if(!authHeader.split(" ")[0].equals("Bearer")){
            return null;
        }
        return authHeader.split(" ")[1];
    }
}
