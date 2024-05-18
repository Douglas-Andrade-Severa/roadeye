package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.AddressDto;
import buildrun.roadeye.rest.dto.AddressUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    public ResponseEntity<?> createAddressByUser(AddressDto addressDto, UUID userId);

    ResponseEntity<?>  getAllAddress();

    ResponseEntity<?> deleteAddress(Long addressId);

    ResponseEntity<?> createAddressBySchool(AddressDto addressDto, Long schoolId);

    ResponseEntity<?> getAddressResponseById(Long addressId);

    ResponseEntity<?> updateAddress(Long addressId, AddressUpdateDto addressUpdateDto);
}
