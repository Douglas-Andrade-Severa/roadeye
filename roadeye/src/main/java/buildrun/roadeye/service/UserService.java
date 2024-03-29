package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);

    List<User> getAllUsers();
}
