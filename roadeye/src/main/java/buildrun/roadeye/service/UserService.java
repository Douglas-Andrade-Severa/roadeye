package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.rest.dto.UserPasswordDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserDto createUser(UserDto userDto);

    ResponseEntity<?> getAllUsers();

    ResponseEntity<?>  deleteUser(UUID userId);

    ResponseEntity<?>  updateUser(UUID userId, UserDto updateUserDto);

    ResponseEntity<?> getUserResponseById(UUID userId);

    ResponseEntity<?> updateUserPassword(UUID userId, UserPasswordDto updateUserDto);
}
