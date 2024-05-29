package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUser_Id(UUID userId);
    Optional<UserAddress> findByUser(User user);
    List<UserAddress> findAllByUser(User user);
    Optional<UserAddress> findByAddressIdAndUserId(Long addressId, UUID userId);
}
