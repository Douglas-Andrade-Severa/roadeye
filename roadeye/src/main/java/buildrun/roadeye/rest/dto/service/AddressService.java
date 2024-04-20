package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.rest.dto.AddressDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    public AddressDto createAddressByUser(AddressDto addressDto, UUID userId);

    List<Address> getAllAddress();

    boolean deleteAddress(Long addressId);

    Address getUserById(Long addressId);

    AddressDto createAddressBySchool(AddressDto addressDto, Long schoolId);

    ResponseEntity<?> getAddressResponseById(Long addressId);

    ResponseEntity<?> updateAddress(Long addressId, AddressUpdateDto addressUpdateDto);
}
