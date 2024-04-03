package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.repository.AddressRepository;
import buildrun.roadeye.domain.repository.SchoolAddressRepository;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.rest.dto.SchoolAddressDto;
import buildrun.roadeye.service.SchoolAddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolAddressServiceImplementation implements SchoolAddressService {
    private final SchoolAddressRepository schoolAddressRepository;
    private final SchoolRepository schoolRepository;
    private final AddressRepository addressRepository;
    public SchoolAddressServiceImplementation(SchoolAddressRepository schoolAddressRepository, SchoolRepository schoolRepository, AddressRepository addressRepository) {
        this.schoolAddressRepository = schoolAddressRepository;
        this.schoolRepository = schoolRepository;
        this.addressRepository = addressRepository;
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

    @Override
    @Transactional
    public void deleteUserAddress(Long userAddressId) {
        SchoolAddress schoolAddress = schoolAddressRepository.findById(userAddressId).orElseThrow(() -> new EntityNotFoundException("School or Address not found"));
        schoolAddressRepository.delete(schoolAddress);
    }

    @Override
    @Transactional
    public SchoolAddress getUserAddressById(Long schoolAddressId) {
        return schoolAddressRepository.findById(schoolAddressId).orElseThrow(() -> new EntityNotFoundException("SchoolAddress not found"));
    }

    @Override
    @Transactional
    public SchoolAddress updateSchoolAddress(Long schoolAddressId, SchoolAddressDto updateSchoolAddressDto) {
        Long schoolId = updateSchoolAddressDto.schoolId();
        Long addressId = updateSchoolAddressDto.addressId();

        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found");
        }
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }

        Optional<SchoolAddress> optionalSchoolAddress = schoolAddressRepository.findById(schoolAddressId);
        if (optionalSchoolAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserAddress not found");
        }
        School school = optionalSchool.get();
        Address address = optionalAddress.get();

        SchoolAddress schoolAddress = optionalSchoolAddress.get();
        schoolAddress.setSchool(school);
        schoolAddress.setAddress(address);

        SchoolAddress savedAddress = schoolAddressRepository.save(schoolAddress);
        return savedAddress;
    }
}
