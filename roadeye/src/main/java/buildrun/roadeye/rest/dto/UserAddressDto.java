package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.UserAddress;

import java.util.UUID;

public record UserAddressDto(UUID userId, Long addressId) {
    public static UserAddressDto fromEntity(UserAddress savedAddress) {
        return new UserAddressDto(
                savedAddress.getUser().getId(),
                savedAddress.getAddress().getId()
        );
    }
}
