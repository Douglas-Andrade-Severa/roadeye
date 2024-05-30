package buildrun.roadeye.service;

import buildrun.roadeye.rest.dto.AddressActivateDisableDto;
import buildrun.roadeye.rest.dto.AddressDto;
import buildrun.roadeye.rest.dto.AddressUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AddressService {
    ResponseEntity<?> createAddressByUser(AddressDto addressDto, UUID userId);

    ResponseEntity<?>  getAllAddress();

    ResponseEntity<?> deleteAddress(Long addressId);

    ResponseEntity<?> createAddressBySchool(AddressDto addressDto, Long schoolId);

    ResponseEntity<?> getAddressResponseById(Long addressId);

    ResponseEntity<?> updateAddress(Long addressId, AddressUpdateDto addressUpdateDto);

    ResponseEntity<?> updateActivateDisableAddressByUser(AddressActivateDisableDto activateDisable, UUID userId);
}
