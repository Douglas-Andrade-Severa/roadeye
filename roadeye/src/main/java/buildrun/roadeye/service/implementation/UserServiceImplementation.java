package buildrun.roadeye.service.implementation;

import buildrun.roadeye.Uteis.Functions;
import buildrun.roadeye.domain.entity.ErrorResponse;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.rest.dto.UserPasswordDto;
import buildrun.roadeye.rest.dto.UserTokenPushDto;
import buildrun.roadeye.rest.dto.UserUpdateDto;
import buildrun.roadeye.service.UserService;
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

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        try {
            var userFromLogin = userRepository.findByLogin(userDto.login());
            if (userFromLogin.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Login already exists.");
            }
            // Validação dos campos obrigatórios
            if (userDto.name() == null || userDto.name().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Name cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (userDto.login() == null || userDto.login().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Login cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (userDto.password() == null || userDto.password().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Password cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (userDto.role() == null || userDto.role().getRole().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Role cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(RoleEnum.isRoleValid(userDto.role()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid role");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            if (userDto.lastName() == null || userDto.lastName().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Last name cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (userDto.email() == null || userDto.email().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("E-mail cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(Functions.isValidEmail(userDto.email()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid email");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            if (userDto.cpf() == null || userDto.cpf().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("CPF cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else {
                if (Functions.isCPF(userDto.cpf()) == false) {
                    ErrorResponse errorResponse = new ErrorResponse("Invalid CPF");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

                }
            }
            if (userDto.phone() == null || userDto.phone().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Phone cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(Functions.validarTelefone(userDto.phone()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid Phone");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            if (userDto.statusEnum() == null || userDto.statusEnum().getStatus().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Status cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(StatusEnum.isStatusValid(userDto.statusEnum())){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid Status");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            User user = new User();
            setUserService(user, userDto);
            return ResponseEntity.ok(userRepository.save(user));
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateUser(UUID userId, UserUpdateDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            // Validação dos campos obrigatórios
            if (updateUserDto.name() == null || updateUserDto.name().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Name cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (updateUserDto.email() == null || updateUserDto.email().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("E-mail cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(Functions.isValidEmail(updateUserDto.email()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid email");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            if (updateUserDto.phone() == null || updateUserDto.phone().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Phone cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(Functions.validarTelefone(updateUserDto.phone()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid Phone");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            if (updateUserDto.cpf() == null || updateUserDto.cpf().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("CPF cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else {
                if (Functions.isCPF(updateUserDto.cpf()) == false) {
                    ErrorResponse errorResponse = new ErrorResponse("Invalid CPF");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

                }
            }
            User user = optionalUser.get();
            setUserUpdateService(user, updateUserDto);
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @Override
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No users found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
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
    public ResponseEntity<?> updateUserPassword(UUID userId, UserPasswordDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            if (updateUserDto.password() == null || updateUserDto.password().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Password cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(updateUserDto.password()));
            return ResponseEntity.ok(userRepository.save(user));
        }else{
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateUserTokenPush(UUID userId, UserTokenPushDto tokenPushDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            if (tokenPushDto.tokenPush() == null || tokenPushDto.tokenPush().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Token Push cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            User user = optionalUser.get();
            user.setTokenPush(tokenPushDto.tokenPush());
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

    private void setUserUpdateService(User user, UserUpdateDto userDto) {
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setCpf(userDto.cpf());
        user.setPhone(userDto.phone());
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
