package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.StatusEnum;
public record AddressDto(
        Long addressId,
        String postCode,
        String street,
        String neighborhood,
        String city,
        String state,
        String country,
        String complement,
        Long number,
        StatusEnum statusEnum,
        User user,
        Double latitude,
        Double longitude
) {
    public String getFullAddress() {
        return street + " " + number + ", " + neighborhood + ", " + city + ", " + state + " " + postCode + ", " + country;
    }

    public static AddressDto fromEntity(Address address) {
        return new AddressDto(
                address.getAddressId(),
                address.getPostCode(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getComplement(),
                address.getNumber(),
                address.getStatusEnum(),
                address.getUser(),
                address.getLatitude(),
                address.getLongitude()
        );
    }
}
