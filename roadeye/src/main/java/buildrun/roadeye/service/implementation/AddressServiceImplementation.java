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

    @Transactional
    public AddressDto createAddressByUser(AddressDto addressDto, UUID userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
            Address address = new Address();
            setAddressService(address, addressDto);
            Address savedAddress = addressRepository.save(address);
            UserAddress userAddress = new UserAddress();
            userAddress.setUser(user);
            userAddress.setAddress(savedAddress);
            userAddressRepository.save(userAddress);
            return AddressDto.fromEntity(savedAddress);
        }catch (ResponseStatusException ex){
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Address does not exist."));
        addressRepository.delete(address);
    }

    @Override
    @Transactional
    public Address updateAddress(Long addressId, AddressDto addressDto) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Address does not exist."));
        setAddressService(address, addressDto);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address getUserById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Address does not exist."));
    }

    @Override
    @Transactional
    public AddressDto createAddressBySchool(AddressDto addressDto, Long schoolId) {
        try {
            School school = schoolRepository.findById(schoolId).orElseThrow(() -> new EntityNotFoundException("School not found with id: " + schoolId));
            Address address = new Address();
            setAddressService(address, addressDto);
            Address savedAddress = addressRepository.save(address);
            SchoolAddress schoolAddress = new SchoolAddress();
            schoolAddress.setSchool(school);
            schoolAddress.setAddress(savedAddress);
            schoolAddressRepository.save(schoolAddress);
            return AddressDto.fromEntity(savedAddress);
        }catch (ResponseStatusException ex){
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Transactional
    private void setAddressService(Address address, AddressDto addressDto) {
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
