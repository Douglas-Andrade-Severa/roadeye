package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.repository.*;
import buildrun.roadeye.rest.dto.AddressDto;
import buildrun.roadeye.rest.dto.GeolocationDto;
import buildrun.roadeye.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    public AddressDto createAddressByUser(AddressDto addressDto, UUID userId) {
        User user = getUserById(userId);
        Address address = createAddress(addressDto);
        UserAddress userAddress = new UserAddress();
        userAddress.setUser(user);
        userAddress.setAddress(address);
        userAddressRepository.save(userAddress);
        return AddressDto.fromEntity(address);
    }

    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = getAddressById(addressId);
        addressRepository.delete(address);
    }

    @Override
    public Address updateAddress(Long addressId, AddressDto addressDto) {
        Address address = getAddressById(addressId);
        setAddressDetails(address, addressDto);
        return addressRepository.save(address);
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address does not exist."));
    }

    @Override
    public Address getUserById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Address does not exist."));
    }
    @Override
    public AddressDto createAddressBySchool(AddressDto addressDto, Long schoolId) {
        School school = getSchoolById(schoolId);
        Address address = createAddress(addressDto);
        SchoolAddress schoolAddress = new SchoolAddress();
        schoolAddress.setSchool(school);
        schoolAddress.setAddress(address);
        schoolAddressRepository.save(schoolAddress);
        return AddressDto.fromEntity(address);
    }

    private User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
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

    private void setAddressDetails(Address address, AddressDto addressDto) {
        GeolocationDto geolocation = geocodingService.getGeolocation(addressDto.getFullAddress());
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
}
