package buildrun.roadeye.rest.dto.service;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import buildrun.roadeye.rest.dto.FatherSonRelationshipDto;

import java.util.List;
import java.util.UUID;

public interface FatherSonRelationshipService {
    List<FatherSonRelationship> getAllFatherSonRelationship();

    FatherSonRelationship getFatherSonRelationshipById(Long fatherSonRelationshipId);

    List<FatherSonRelationship> findFatherSonRelationshipByUserId(FatherSonRelationshipDto fatherSonRelationshipDto);

    void deleteFatherSonRelationship(Long fatherSonRelationshipId);

    FatherSonRelationship updateFatherSonRelationship(Long fatherSonRelationshipId, FatherSonRelationshipDto fatherSonRelationshipDto);

    FatherSonRelationship createFatherSonRelationship(FatherSonRelationshipDto fatherSonRelationship);
}
