package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.enums.StatusEnum;
public record AddressDto(
        String postCode,
        String street,
        String neighborhood,
        String city,
        String state,
        String country,
        String complement,
        Long number,
        StatusEnum statusEnum,
        Double latitude,
        Double longitude
) {
    public static AddressDto fromEntity(Address address) {
        return new AddressDto(
                address.getPostCode(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getComplement(),
                address.getNumber(),
                address.getStatusEnum(),
                address.getLatitude(),
                address.getLongitude()
        );
    }
}
