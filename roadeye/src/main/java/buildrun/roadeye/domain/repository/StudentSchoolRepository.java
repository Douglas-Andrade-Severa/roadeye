package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.StudentSchool;
import buildrun.roadeye.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentSchoolRepository extends JpaRepository<StudentSchool, Long> {
    List<StudentSchool> findByUser_Id(UUID userId);
}
