package buildrun.roadeye.rest.dto.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.repository.AddressRepository;
import buildrun.roadeye.domain.repository.UserAddressRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserAddressDto;
import buildrun.roadeye.rest.dto.service.UserAddressService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class UserAddressServiceImplementation implements UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserAddressServiceImplementation(UserAddressRepository userAddressRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public UserAddressDto createUserAddress(UserAddressDto userAddressDto) {
        UUID userId = userAddressDto.userId();
        Long addressId = userAddressDto.addressId();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }

        User user = optionalUser.get();
        Address address = optionalAddress.get();

        UserAddress userAddress = new UserAddress();
        userAddress.setUser(user);
        userAddress.setAddress(address);
        UserAddress savedAddress = userAddressRepository.save(userAddress);
        return userAddressDto.fromEntity(savedAddress);
    }
    @Override
    public ResponseEntity<?> getAllUsersAddress() {
        List<UserAddress> userAddresses = userAddressRepository.findAll();
        if (!userAddresses.isEmpty()) {
            return ResponseEntity.ok(userAddresses);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No userAddresses found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> deleteUserAddress(Long userAddressId) {
        Optional<UserAddress> optionalUserAddress = userAddressRepository.findById(userAddressId);
        if (optionalUserAddress.isPresent()) {
            UserAddress userAddress = optionalUserAddress.get();
            userAddressRepository.delete(userAddress);
            ErrorResponse errorResponse = new ErrorResponse("User Address deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User Address does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateUserAddress(Long userAddressId, UserAddressDto updateUserAddressDto) {
        UUID userId = updateUserAddressDto.userId();
        Long addressId = updateUserAddressDto.addressId();

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        Optional<UserAddress> optionalUserAddress = userAddressRepository.findById(userAddressId);
        if (optionalUserAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("UserAddress does not exist."));
        }
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User does not exist."));
        }
        if (optionalAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Address does not exist."));
        }
        User user = optionalUser.get();
        Address address = optionalAddress.get();
        UserAddress userAddress = optionalUserAddress.get();
        userAddress.setUser(user);
        userAddress.setAddress(address);
        return ResponseEntity.ok(userAddressRepository.save(userAddress));
    }

    @Override
    public ResponseEntity<?> getUserAddressById(Long userAddressId) {
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElse(null);
        if (userAddress != null) {
            return ResponseEntity.ok(userAddress);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User Address does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public List<UserAddress> findAddressesByUser_Id(UUID userId) {
        return userAddressRepository.findByUser_Id(userId);
    }

}
