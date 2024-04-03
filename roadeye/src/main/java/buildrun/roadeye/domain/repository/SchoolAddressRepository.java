package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.SchoolAddress;
import buildrun.roadeye.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolAddressRepository extends JpaRepository<SchoolAddress, Long> {
    List<SchoolAddress> findBySchool_Id(Long schoolId);
}
