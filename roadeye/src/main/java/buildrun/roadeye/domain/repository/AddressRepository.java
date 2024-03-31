package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
