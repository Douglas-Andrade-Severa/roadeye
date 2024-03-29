package buildrun.roadeye.rest.controller;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.rest.dto.UserDto;
import buildrun.roadeye.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    private UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
}
