package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.SchoolAddressDto;

import java.util.List;

public interface SchoolAddressService {
    List<SchoolAddress> getAllSchoolAddress();

    List<SchoolAddress> findAddressesBySchool_Id(Long schoolId);

    void deleteUserAddress(Long userAddressId);

    SchoolAddress getUserAddressById(Long schoolAddressId);

    SchoolAddress updateSchoolAddress(Long schoolAddressId, SchoolAddressDto updateSchoolAddressDto);
}
