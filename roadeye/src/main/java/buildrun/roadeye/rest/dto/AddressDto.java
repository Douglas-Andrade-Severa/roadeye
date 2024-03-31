package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.User;
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
        Double latitude,
        Double longitude,
        StatusEnum statusEnum,
        User user
) {}
