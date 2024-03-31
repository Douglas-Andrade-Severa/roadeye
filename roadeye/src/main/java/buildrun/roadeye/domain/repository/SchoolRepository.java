package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.School;
import buildrun.roadeye.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByName(String name);
}
