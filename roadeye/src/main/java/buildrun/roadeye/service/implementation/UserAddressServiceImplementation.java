package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.domain.repository.AddressRepository;
import buildrun.roadeye.domain.repository.UserAddressRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.UserAddressDto;
import buildrun.roadeye.service.UserAddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
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
    @Transactional
    public List<UserAddress> getAllUsersAddress() {
        return userAddressRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUserAddress(Long userAddressId) {
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(() -> new EntityNotFoundException("User or Address not found"));
        userAddressRepository.delete(userAddress);
    }

    @Override
    @Transactional
    public UserAddress updateUserAddress(Long userAddressId, UserAddressDto updateUserAddressDto) {
        UUID userId = updateUserAddressDto.userId();
        Long addressId = updateUserAddressDto.addressId();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }

        Optional<UserAddress> optionalUserAddress = userAddressRepository.findById(userAddressId);
        if (optionalUserAddress.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserAddress not found");
        }
        User user = optionalUser.get();
        Address address = optionalAddress.get();

        UserAddress userAddress = optionalUserAddress.get();
        userAddress.setUser(user);
        userAddress.setAddress(address);

        UserAddress savedAddress = userAddressRepository.save(userAddress);
        return savedAddress;
    }

    @Override
    @Transactional
    public UserAddress getUserAddressById(Long userAddressId) {
        return userAddressRepository.findById(userAddressId).orElseThrow(() -> new EntityNotFoundException("UserAddress not found"));
    }

    @Override
    @Transactional
    public List<UserAddress> findAddressesByUser_Id(UUID userId) {
        return userAddressRepository.findByUser_Id(userId);
    }
}
