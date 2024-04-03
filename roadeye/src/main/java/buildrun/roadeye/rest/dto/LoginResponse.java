package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.enums.RoleEnum;

import java.util.UUID;

public record LoginResponse(String accessToken, UUID userId, RoleEnum roleEnum) {
}
