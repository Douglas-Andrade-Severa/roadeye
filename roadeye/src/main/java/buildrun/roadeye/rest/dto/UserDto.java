package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.enums.RoleEnum;

public record UserDto(
        String name,
        String login,
        String password,
        RoleEnum role
) {
}
