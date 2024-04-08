package buildrun.roadeye.domain.repository;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FatherSonRelationshipRepository extends JpaRepository<FatherSonRelationship, Long> {
    List<FatherSonRelationship> findByResponsible_IdOrStudent_Id(UUID responsibleId, UUID studentId);
}
