package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.rest.dto.AddressDto;

import java.util.List;

public interface AddressService {
    public AddressDto createAddress(AddressDto addressDto);

    List<Address> getAllAddress();

    void deleteAddress(Long addressId);

    Address updateAddress(Long addressId, AddressDto addressDto);

    Address getUserById(Long addressId);
}
