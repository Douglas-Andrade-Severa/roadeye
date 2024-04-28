package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.StudentSchool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSchoolRepository extends JpaRepository<StudentSchool, Long> {
}
