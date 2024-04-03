package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.rest.dto.AddressDto;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    public AddressDto createAddressByUser(AddressDto addressDto, UUID userId);

    List<Address> getAllAddress();

    void deleteAddress(Long addressId);

    Address updateAddress(Long addressId, AddressDto addressDto);

    Address getUserById(Long addressId);

    AddressDto createAddressBySchool(AddressDto addressDto, Long schoolId);
}
