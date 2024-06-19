package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.UserAddressDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserAddressService {
    UserAddressDto createUserAddress(UserAddressDto userAddressDto);

    ResponseEntity<?> getAllUsersAddress();

    ResponseEntity<?> deleteUserAddress(Long userAddressId);

    ResponseEntity<?> updateUserAddress(Long userAddressId, UserAddressDto updateUserAddressDto);

    ResponseEntity<?> getUserAddressById(Long userAddressId);

    List<UserAddress> findAddressesByUser_Id(UUID userId);
}
