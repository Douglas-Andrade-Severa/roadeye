package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.SchoolAddress;

import java.util.List;

public interface SchoolAddressService {
    List<SchoolAddress> getAllSchoolAddress();

    List<SchoolAddress> findAddressesBySchool_Id(Long schoolId);
}
