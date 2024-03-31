package buildrun.roadeye.service;

import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.UserAddressDto;

import java.util.List;

public interface UserAddressService {
    UserAddressDto createUserAddress(UserAddressDto userAddressDto);

    List<UserAddress> getAllUsersAddress();

    void deleteUserAddress(Long userAddressId);

    UserAddress updateUserAddress(Long userAddressId, UserAddressDto updateUserAddressDto);

    UserAddress getUserAddressById(Long userAddressId);
}
