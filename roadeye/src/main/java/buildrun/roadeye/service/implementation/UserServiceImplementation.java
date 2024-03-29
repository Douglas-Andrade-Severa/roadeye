package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        try {
            User userFromLogin = userRepository.findByLogin(userDto.login());
            if (userFromLogin != null) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Login already exists.");
            }
            User user = new User(userDto.name(), userDto.login(), passwordEncoder.encode(userDto.password()), userDto.role());
            User newUser = userRepository.save(user);
            return new UserDto(newUser.getName(), newUser.getLogin(), newUser.getPassword(), userDto.role());
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }
}
