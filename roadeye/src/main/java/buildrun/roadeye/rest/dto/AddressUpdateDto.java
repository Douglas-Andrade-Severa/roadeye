package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.enums.StatusEnum;

public record AddressUpdateDto(String postCode,
                               String street,
                               String neighborhood,
                               String city,
                               String state,
                               String country,
                               String complement,
                               Long number,
                               StatusEnum statusEnum) {
}

