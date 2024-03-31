package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;

import java.util.UUID;

public record UserAddressDto(UUID userId, Long addressId) {
    public static UserAddressDto fromEntity(UserAddress savedAddress) {
        return new UserAddressDto(
                savedAddress.getUser().getUserId(),
                savedAddress.getAddress().getAddressId()
        );
    }
}
