package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;

public record UserDto(
        String name,
        String login,
        String password,
        RoleEnum role,
        String lastName,
        String email,
        String cpf,
        String phone,
        String photo,
        StatusEnum statusEnum
) {
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getLastName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhone(),
                user.getPhoto(),
                user.getStatusEnum()
        );
    }
}
