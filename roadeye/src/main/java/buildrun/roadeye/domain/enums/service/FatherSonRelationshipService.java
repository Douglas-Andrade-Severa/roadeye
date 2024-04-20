package buildrun.roadeye.domain.enums.service;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import buildrun.roadeye.rest.dto.FatherSonRelationshipDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface FatherSonRelationshipService {
    ResponseEntity<?> getAllFatherSonRelationship();

    ResponseEntity<?> getFatherSonRelationshipById(Long fatherSonRelationshipId);

    ResponseEntity<?> findFatherSonRelationshipByUserId(FatherSonRelationshipDto fatherSonRelationshipDto);

    ResponseEntity<?> deleteFatherSonRelationship(Long fatherSonRelationshipId);

    ResponseEntity<?> updateFatherSonRelationship(Long fatherSonRelationshipId, FatherSonRelationshipDto fatherSonRelationshipDto);

    ResponseEntity<?> createFatherSonRelationship(FatherSonRelationshipDto fatherSonRelationship);
}
