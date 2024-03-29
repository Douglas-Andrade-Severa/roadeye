package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        try {
            var userFromLogin = userRepository.findByLogin(userDto.login());
            if (userFromLogin.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Login already exists.");
            }
            User user = new User();
            user.setName(userDto.name());
            user.setLogin(userDto.login());
            user.setPassword(passwordEncoder.encode(userDto.password()));
            user.setRole(userDto.role());

            User newUser = userRepository.save(user);
            return new UserDto(newUser.getName(), newUser.getLogin(), newUser.getPassword(), userDto.role());
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
