package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
