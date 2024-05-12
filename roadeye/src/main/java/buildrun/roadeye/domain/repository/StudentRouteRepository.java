package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.entity.StudentRoute;
import buildrun.roadeye.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRouteRepository extends JpaRepository<StudentRoute, Long> {
    List<StudentRoute> findByUser_Id(UUID userId);
    List<StudentRoute> findBySchool_Id(Long school);
    boolean existsByUserAndSchool(User user, School school);
}
