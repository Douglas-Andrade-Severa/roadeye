package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String axNotFound = "User not found";

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
            setUserService(user, userDto);
            User newUser = userRepository.save(user);
            return new UserDto(
                    newUser.getName(),
                    newUser.getLogin(),
                    newUser.getPassword(),
                    userDto.role(),
                    newUser.getLastName(),
                    newUser.getEmail(),
                    newUser.getCpf(),
                    newUser.getPhone(),
                    newUser.getPhoto(),
                    newUser.getStatusEnum());
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(axNotFound));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User updateUser(UUID userId, UserDto updateUserDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(axNotFound));
        setUserService(user, updateUserDto);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(axNotFound));
    }

    @Transactional
    private void setUserService(User user, UserDto userDto) {
        user.setName(userDto.name());
        user.setLogin(userDto.login());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(userDto.role());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setCpf(userDto.cpf());
        user.setPhone(userDto.phone());
        user.setPhoto(userDto.photo());
        user.setStatusEnum(userDto.statusEnum());
    }

}
