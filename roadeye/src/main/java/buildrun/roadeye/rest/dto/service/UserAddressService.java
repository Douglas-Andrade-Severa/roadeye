package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;
import buildrun.roadeye.rest.dto.UserAddressDto;

import java.util.List;
import java.util.UUID;

public interface UserAddressService {
    UserAddressDto createUserAddress(UserAddressDto userAddressDto);

    List<UserAddress> getAllUsersAddress();

    void deleteUserAddress(Long userAddressId);

    UserAddress updateUserAddress(Long userAddressId, UserAddressDto updateUserAddressDto);

    UserAddress getUserAddressById(Long userAddressId);

    List<UserAddress> findAddressesByUser_Id(UUID userId);
}
