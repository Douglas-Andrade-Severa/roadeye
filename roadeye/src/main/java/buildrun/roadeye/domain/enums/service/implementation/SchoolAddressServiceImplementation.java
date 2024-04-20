package buildrun.roadeye.domain.enums.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.enums.service.SchoolAddressService;
import buildrun.roadeye.domain.repository.AddressRepository;
import buildrun.roadeye.domain.repository.SchoolAddressRepository;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.rest.dto.SchoolAddressDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public ResponseEntity<?> getAllSchoolAddress() {
        List<SchoolAddress> schoolAddresses = schoolAddressRepository.findAll();
        if (!schoolAddresses.isEmpty()) {
            return ResponseEntity.ok(schoolAddresses);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No schoolAddresses found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public List<SchoolAddress> findAddressesBySchool_Id(Long schoolId) {
        return schoolAddressRepository.findBySchool_Id(schoolId);
    }

    @Override
    public ResponseEntity<?> deleteUserAddress(Long userAddressId) {
        Optional<SchoolAddress> optionalSchoolAddress= schoolAddressRepository.findById(userAddressId);
        if (optionalSchoolAddress.isPresent()) {
            SchoolAddress schoolAddress = optionalSchoolAddress.get();
            schoolAddressRepository.delete(schoolAddress);
            ErrorResponse errorResponse = new ErrorResponse("School Address deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("School Address does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateSchoolAddress(Long schoolAddressId, SchoolAddressDto updateSchoolAddressDto) {
        Long schoolId = updateSchoolAddressDto.schoolId();
        Long addressId = updateSchoolAddressDto.addressId();
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        Optional<SchoolAddress> optionalSchoolAddress = schoolAddressRepository.findById(schoolAddressId);
        if (optionalSchoolAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("SchoolAddress does not exist."));
        }
        if (optionalSchool.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("School does not exist."));
        }
        if (optionalAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Address does not exist."));
        }
        School school = optionalSchool.get();
        Address address = optionalAddress.get();
        SchoolAddress schoolAddress = optionalSchoolAddress.get();
        schoolAddress.setSchool(school);
        schoolAddress.setAddress(address);
        return ResponseEntity.ok(schoolAddressRepository.save(schoolAddress));
    }

    @Override
    public ResponseEntity<?> getSchoolAddressById(Long schoolAddressId) {
        SchoolAddress schoolAddress = schoolAddressRepository.findById(schoolAddressId).orElse(null);
        if (schoolAddress != null) {
            return ResponseEntity.ok(schoolAddress);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("School Address does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
