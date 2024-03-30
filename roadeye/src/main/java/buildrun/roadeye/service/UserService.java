package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserDto createUser(UserDto userDto);

    List<User> getAllUsers();

    void deleteUser(UUID userId);

    User updateUser(UUID userId, UserDto updateUserDto);

    User getUserById(UUID userId);
}
