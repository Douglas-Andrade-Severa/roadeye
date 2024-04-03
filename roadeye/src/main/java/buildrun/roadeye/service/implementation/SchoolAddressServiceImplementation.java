package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.repository.SchoolAddressRepository;
import buildrun.roadeye.service.SchoolAddressService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SchoolAddressServiceImplementation implements SchoolAddressService {
    private final SchoolAddressRepository schoolAddressRepository;

    public SchoolAddressServiceImplementation(SchoolAddressRepository schoolAddressRepository) {
        this.schoolAddressRepository = schoolAddressRepository;
    }

    @Override
    @Transactional
    public List<SchoolAddress> getAllSchoolAddress() {
        return schoolAddressRepository.findAll();
    }

    @Override
    @Transactional
    public List<SchoolAddress> findAddressesBySchool_Id(Long schoolId) {
        return schoolAddressRepository.findBySchool_Id(schoolId);
    }
}
