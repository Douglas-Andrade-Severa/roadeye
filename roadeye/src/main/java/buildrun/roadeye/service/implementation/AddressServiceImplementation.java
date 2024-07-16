package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.repository.*;
import buildrun.roadeye.rest.dto.*;
import buildrun.roadeye.service.AddressService;
import buildrun.roadeye.utils.Functions;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AddressServiceImplementation implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final GoogleGeocodingService geocodingService;
    private final UserAddressRepository userAddressRepository;
    private final SchoolAddressRepository schoolAddressRepository;
    private final SchoolRepository schoolRepository;
    public AddressServiceImplementation(AddressRepository addressRepository, UserRepository userRepository, GoogleGeocodingService geocodingService, UserAddressRepository userAddressRepository, SchoolAddressRepository schoolAddressRepository, SchoolRepository schoolRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.geocodingService = geocodingService;
        this.userAddressRepository = userAddressRepository;
        this.schoolAddressRepository = schoolAddressRepository;
        this.schoolRepository = schoolRepository;
    }
    @Override
    public ResponseEntity<?> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        if (!addresses.isEmpty()) {
            return ResponseEntity.ok(addresses);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No addresses found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> deleteAddress(Long addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(addressId);
            ErrorResponse errorResponse = new ErrorResponse("Address deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Address does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @Override
    public ResponseEntity<?> updateAddress(Long addressId, AddressUpdateDto addressUpdateDto) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();

            System.out.println("******** ANTES DE EXECUTAR LÓGICA UPDATE ********");
            System.out.println("ID: "+addressId);
            System.out.println("CEP: "+addressUpdateDto.postCode());
            System.out.println("street: "+addressUpdateDto.street());
            System.out.println("neighborhood: "+addressUpdateDto.neighborhood());
            System.out.println("city: "+addressUpdateDto.city());
            System.out.println("state: "+addressUpdateDto.state());
            System.out.println("country: "+addressUpdateDto.country());
            System.out.println("complement: "+addressUpdateDto.complement());
            System.out.println("number: "+addressUpdateDto.number());
            System.out.println("Status: "+addressUpdateDto.statusEnum().getStatus());


            if (addressUpdateDto.postCode() == null || addressUpdateDto.postCode().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Post code cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressUpdateDto.street() == null || addressUpdateDto.street().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Street cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressUpdateDto.neighborhood() == null || addressUpdateDto.neighborhood().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Neighborhood cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressUpdateDto.city() == null || addressUpdateDto.city().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("City cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressUpdateDto.state() == null || addressUpdateDto.state().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("State cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressUpdateDto.country() == null || addressUpdateDto.country().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Country cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressUpdateDto.number() == null) {
                ErrorResponse errorResponse = new ErrorResponse("Number cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            if (addressUpdateDto.statusEnum().getStatus() == null || addressUpdateDto.statusEnum().getStatus().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Status cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(StatusEnum.isStatusValid(addressUpdateDto.statusEnum()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid Status");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }
            setAddressDetailsUpdate(address, addressUpdateDto);
            Address updatedAddress = addressRepository.save(address);
            return ResponseEntity.ok(updatedAddress);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Address not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getAddressResponseById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Address does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    private School getSchoolById(Long schoolId) {
        return schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + schoolId));
    }

    private Address createAddress(AddressDto addressDto) {
        Address address = new Address();
        setAddressDetails(address, addressDto);
        return addressRepository.save(address);
    }

    private void setAddressDetailsUpdate(Address address, AddressUpdateDto addressUpdateDto) {
        GeolocationDto geolocation = geocodingService.getGeolocation(getFullAddress(addressUpdateDto.street(),addressUpdateDto.number(), addressUpdateDto.neighborhood(), addressUpdateDto.city(), addressUpdateDto.state(), addressUpdateDto.postCode(), addressUpdateDto.country()));
        address.setPostCode(addressUpdateDto.postCode());
        address.setStreet(addressUpdateDto.street());
        address.setNeighborhood(addressUpdateDto.neighborhood());
        address.setCity(addressUpdateDto.city());
        address.setState(addressUpdateDto.state());
        address.setCountry(addressUpdateDto.country());
        address.setComplement(addressUpdateDto.complement());
        address.setNumber(addressUpdateDto.number());
        address.setStatusEnum(addressUpdateDto.statusEnum());
        if (geolocation != null) {
            address.setLatitude(geolocation.latitude());
            address.setLongitude(geolocation.longitude());
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to obtain geolocation coordinates.");
        }
    }

    private void setAddressDetails(Address address, AddressDto addressDto) {
        GeolocationDto geolocation = geocodingService.getGeolocation(getFullAddress(addressDto.street(),addressDto.number(), addressDto.neighborhood(), addressDto.city(), addressDto.state(), addressDto.postCode(), addressDto.country()));
        address.setPostCode(addressDto.postCode());
        address.setStreet(addressDto.street());
        address.setNeighborhood(addressDto.neighborhood());
        address.setCity(addressDto.city());
        address.setState(addressDto.state());
        address.setCountry(addressDto.country());
        address.setComplement(addressDto.complement());
        address.setNumber(addressDto.number());
        address.setStatusEnum(addressDto.statusEnum());
        if (geolocation != null) {
            address.setLatitude(geolocation.latitude());
            address.setLongitude(geolocation.longitude());
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to obtain geolocation coordinates.");
        }
    }

    public ResponseEntity<?> createAddressByUser(AddressDto addressDto, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<UserAddress> existingUserAddresses = userAddressRepository.findAllByUser(user);
            existingUserAddresses.forEach(userAddress -> {
                Address address = userAddress.getAddress();
                address.setStatusEnum(StatusEnum.DISABLED);
                addressRepository.save(address);
            });

            System.out.println("******** ANTES DE EXECUTAR LÓGICA CREATE ********");
            System.out.println("ID user: "+userId);
            System.out.println("CEP: "+addressDto.postCode());
            System.out.println("street: "+addressDto.street());
            System.out.println("neighborhood: "+addressDto.neighborhood());
            System.out.println("city: "+addressDto.city());
            System.out.println("state: "+addressDto.state());
            System.out.println("country: "+addressDto.country());
            System.out.println("complement: "+addressDto.complement());
            System.out.println("number: "+addressDto.number());
            System.out.println("Status: "+addressDto.statusEnum().getStatus());

            if (addressDto.postCode() == null || addressDto.postCode().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Post code cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressDto.street() == null || addressDto.street().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Street cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressDto.neighborhood() == null || addressDto.neighborhood().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Neighborhood cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressDto.city() == null || addressDto.city().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("City cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressDto.state() == null || addressDto.state().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("State cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressDto.country() == null || addressDto.country().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Country cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (addressDto.number() == null) {
                ErrorResponse errorResponse = new ErrorResponse("Number cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            if (addressDto.statusEnum().getStatus() == null || addressDto.statusEnum().getStatus().isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Status cannot be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }else{
                if(StatusEnum.isStatusValid(addressDto.statusEnum()) == false){
                    ErrorResponse errorResponse = new ErrorResponse("Invalid Status");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }

            Address address = createAddress(addressDto);
            UserAddress userAddress = new UserAddress();
            userAddress.setUser(user);
            userAddress.setAddress(address);
            return ResponseEntity.ok(userAddressRepository.save(userAddress));
        }else{
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateActivateDisableAddressByUser(AddressActivateDisableDto activateDisable, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<UserAddress> optionalUserAddress = userAddressRepository.findByAddressIdAndUserId(activateDisable.idAddress(), userId);
            if (!optionalUserAddress.isPresent()) {
                ErrorResponse errorResponse = new ErrorResponse("Address not related to the user informed.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            System.out.println("******** ANTES DE EXECUTAR LÓGICA updateActivateDisableAddressByUser ********");
            System.out.println("ID user: "+userId);
            System.out.println("ID address: "+activateDisable.idAddress());

            List<UserAddress> existingUserAddresses = userAddressRepository.findAllByUser(user);
            existingUserAddresses.forEach(userAddress -> {
                Address address = userAddress.getAddress();
                address.setStatusEnum(StatusEnum.DISABLED);
                addressRepository.save(address);
            });

            Optional<Address> optionalAddress = addressRepository.findById(activateDisable.idAddress());
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                address.setStatusEnum(StatusEnum.ACTIVATE);
                Address updatedAddress = addressRepository.save(address);
                return ResponseEntity.ok(updatedAddress);
            } else {
                ErrorResponse errorResponse = new ErrorResponse("Address not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        }else{
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> createAddressByUserByCoordinates(AddressCoordinatesDto coordinatesDto, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        System.out.println("******** ANTES DE EXECUTAR LÓGICA createAddressByUserByCoordinates ********");
        System.out.println("ID user: "+userId);
        System.out.println("Latitude: "+coordinatesDto.latitude());
        System.out.println("Longitude: "+coordinatesDto.longitude());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<UserAddress> existingUserAddresses = userAddressRepository.findAllByUser(user);
            existingUserAddresses.forEach(userAddress -> {
                Address address = userAddress.getAddress();
                address.setStatusEnum(StatusEnum.DISABLED);
                addressRepository.save(address);
            });
            Address address = new Address();
            try {
                if(Functions.isInvalidLatitudeLongitude(coordinatesDto.latitude())){
                    ErrorResponse errorResponse = new ErrorResponse("Latitude cannot be empty.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                }

                if(Functions.isInvalidLatitudeLongitude(coordinatesDto.longitude())){
                    ErrorResponse errorResponse = new ErrorResponse("Longitude cannot be empty.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                }

                address = geocodingService.getAddressFromCoordinates(coordinatesDto.latitude(), coordinatesDto.longitude());
            } catch (Exception e) {
                return new ResponseEntity<>("Unable to obtain address.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            addressRepository.save(address);
            UserAddress userAddress = new UserAddress();
            userAddress.setUser(user);
            userAddress.setAddress(address);
            return ResponseEntity.ok(userAddressRepository.save(userAddress));
        }else{
            ErrorResponse errorResponse = new ErrorResponse("User does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @Override
    public ResponseEntity<?> createAddressBySchool(AddressDto addressDto, Long schoolId) {
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isPresent()) {
            School school = optionalSchool.get();
            Address address = createAddress(addressDto);
            SchoolAddress schoolAddress = new SchoolAddress();
            schoolAddress.setSchool(school);
            schoolAddress.setAddress(address);
            return ResponseEntity.ok(schoolAddressRepository.save(schoolAddress));
        }else{
            ErrorResponse errorResponse = new ErrorResponse("School does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    private String getFullAddress(String street, Long number, String neighborhood, String city, String state, String postCode, String country) {
        return street + " " + number + ", " + neighborhood + ", " + city + ", " + state + " " + postCode + ", " + country;
    }
}
