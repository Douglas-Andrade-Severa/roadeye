package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import buildrun.roadeye.domain.entity.User;

import java.util.UUID;

public record FatherSonRelationshipDto(UUID responsible, UUID student) {
}
