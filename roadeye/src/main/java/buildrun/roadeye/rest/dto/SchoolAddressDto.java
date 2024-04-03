package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;

public record SchoolAddressDto(Long schoolId, Long addressId) {
    public static SchoolAddressDto fromEntity(SchoolAddress savedAddress) {
        return new SchoolAddressDto(
                savedAddress.getSchool().getId(),
                savedAddress.getAddress().getId()
        );
    }
}
