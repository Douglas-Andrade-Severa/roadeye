package buildrun.roadeye.rest.service.implementation;

import buildrun.roadeye.domain.entity.ErrorResponse;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.rest.dto.UserPasswordDto;
import buildrun.roadeye.rest.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String axNotFound = "User not found";

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            var userFromLogin = userRepository.findByLogin(userDto.login());
            if (userFromLogin.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Login already exists.");
            }
            User user = new User();
            setUserService(user, userDto);
            User newUser = userRepository.save(user);
            return userDto.fromEntity(newUser);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> deleteUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
            ErrorResponse errorResponse = new ErrorResponse("User deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(UUID userId, UserDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            setUserService(user, updateUserDto);
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateUserPassword(UUID userId, UserPasswordDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(updateUserDto.password()));
            return ResponseEntity.ok(userRepository.save(user));
        }else{
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getUserResponseById(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

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
