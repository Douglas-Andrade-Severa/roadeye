package buildrun.roadeye.rest.service;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.SchoolAddressDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SchoolAddressService {
    List<SchoolAddress> getAllSchoolAddress();

    List<SchoolAddress> findAddressesBySchool_Id(Long schoolId);

    ResponseEntity<?> deleteUserAddress(Long userAddressId);

    ResponseEntity<?> updateSchoolAddress(Long schoolAddressId, SchoolAddressDto updateSchoolAddressDto);

    ResponseEntity<?> getSchoolAddressById(Long schoolAddressId);
}
