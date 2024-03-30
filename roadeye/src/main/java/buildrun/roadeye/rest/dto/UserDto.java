package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.UUID;

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
}
