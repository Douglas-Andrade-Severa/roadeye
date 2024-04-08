package buildrun.roadeye.rest.dto;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import buildrun.roadeye.domain.entity.User;

public record FatherSonRelationshipDto(User responsible, User student) {
    public static FatherSonRelationshipDto fromEntity(FatherSonRelationship savedFatherSonRelationship) {
        return new FatherSonRelationshipDto(
                savedFatherSonRelationship.getResponsible(),
                savedFatherSonRelationship.getStudent()
        );
    }
}
